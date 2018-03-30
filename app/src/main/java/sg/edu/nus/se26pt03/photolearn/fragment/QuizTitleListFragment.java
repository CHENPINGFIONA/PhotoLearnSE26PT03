package sg.edu.nus.se26pt03.photolearn.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.service.QuizTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

public class QuizTitleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private QuizTitleListAdapter quizTitleListAdapter;
    private QuizTitleService quizTitleService;
    private List<QuizTitle> quizTitles = null;

    private SwipeRefreshLayout srf_learningtitlelist;

    private LearningSession learningSession;
    private boolean updateMode;

    public static QuizTitleListFragment newInstance(LearningSession learningSession) {
        QuizTitleListFragment quizTitleListFragment = new QuizTitleListFragment();
        Bundle args = new Bundle();
        args.putSerializable("learningSession", learningSession);
        quizTitleListFragment.setArguments(args);
        return quizTitleListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupData();
        setupViews();
        setupControls();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    private void setupData() {
        learningSession = (LearningSession) getArguments().getSerializable("learningSession");
        quizTitleService = new QuizTitleService(learningSession);
        quizTitles = learningSession.getQuizTitles();
        quizTitleListAdapter = new QuizTitleListAdapter(quizTitles, new QuizTitleListAdapter.QuizTitleViewHolderClick() {
            @Override
            public void onItemClick(QuizTitleListAdapter.QuizTitleViewHolder viewHolder) {
                QuizTitle quizTitle = quizTitles.get(viewHolder.getAdapterPosition());
                if (App.getCurrentAppMode() == AppMode.PARTICIPENT) {
                    quizTitle.getQuizSubmissionProgress(App.getCurrentUser().getId(), new ServiceCallback<AbstractMap.SimpleEntry<Integer, Integer>>() {
                        @Override
                        public void onComplete(AbstractMap.SimpleEntry<Integer, Integer> data) {
                            if (data.getKey() == data.getValue()) {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Confirmation")
                                        .setMessage("You already submitted this quiz!\nPlease choose the action of your choice to proceed?")
                                        .setPositiveButton("View Summary", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                onSummary(quizTitle, null);

                                            }
                                        })
                                        .setNegativeButton("Retake Quiz", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Delete the quiz and retake
                                            }
                                        }).show();
                            } else {
                                onLoad(quizTitle, null);
                            }
                        }

                        @Override
                        public void onError(int code, String message, String details) {

                        }
                    });
                } else
                    onLoad(quizTitle, null);
            }
        });
    }

    private void refreshData() {
        srf_learningtitlelist.setRefreshing(true);
        quizTitleService.getAllByKeyValue("learningSessionId", learningSession.getId(), new ServiceCallback<List<QuizTitle>>() {
            @Override
            public void onComplete(List<QuizTitle> data) {
                learningSession.removeAllQuizTitle();
                learningSession.addQuizTitles(data);

                quizTitles = learningSession.getQuizTitles();
                refreshViews();
                srf_learningtitlelist.setRefreshing(false);
            }

            @Override
            public void onError(int code, String message, String details) {
                srf_learningtitlelist.setRefreshing(false);
                displayErrorMessage(message);
            }
        });
    }

    private void refreshViews() {
        quizTitleListAdapter.notifyDataSetChanged();
        if (quizTitleListAdapter.quizTitleList.size() == 0)
            this.getView().findViewById(R.id.tv_learningtitlelist_hint).setVisibility(View.VISIBLE);
    }

    private void setupViews() {
        SearchView svSearchView = getView().findViewById(R.id.sv_title);
        svSearchView.setVisibility(View.GONE);
    }

    private void setupControls() {
        RecyclerView recyclerView = getView().findViewById(R.id.rv_learning_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(quizTitleListAdapter);
        final SwipeController swipeController = new SwipeController(0, (App.getCurrentAppMode()== AppMode.TRAINER ? R.layout.partial_swipe_item : 0)) {
            @Override
            public void onRevealInflated(View view, int position) {
                if (view instanceof LinearLayout) {
                    QuizTitle title = quizTitleListAdapter.quizTitleList.get(position);
                    LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
                    linearLayout.findViewWithTag("edit").setVisibility(title.getCreatedBy().equals(App.getCurrentUser().getId()) ? View.VISIBLE : View.GONE);
                    linearLayout.findViewWithTag("delete").setVisibility(title.getCreatedBy().equals(App.getCurrentUser().getId()) ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onClicked(Object tag, final int position) {
                final QuizTitle title = quizTitleListAdapter.quizTitleList.get(position);
                switch (tag.toString()) {
                    case "delete":
                        new AlertDialog.Builder(getContext())
                                .setTitle("Title")
                                .setMessage("Are you sure you wanted to delete this learning title?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        learningSession.deleteQuizTitle(title.getId());
                                        return;
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }).show();

                        break;
                    case "edit":
                        showDialogue(title);
                        break;
                }
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration()

        {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        srf_learningtitlelist = getView().findViewById(R.id.srf_learningtitlelist);
        srf_learningtitlelist.setOnRefreshListener(this);
        srf_learningtitlelist.post(new Runnable() {
            @Override
            public void run() {
                srf_learningtitlelist.setRefreshing(true);
                refreshData();
            }
        });

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_learningtitlelist);
        floatingActionButton.setVisibility(App.getCurrentAppMode() == AppMode.TRAINER ? View.VISIBLE : View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                showDialogue(new QuizTitle());
            }
        });
    }

    private void showDialogue(final QuizTitle title) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.quiz_title);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        etContent.setHint(R.string.enter_title);

        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        if (title.getId() != null) {
            updateMode = true;
            etContent.setText(title.getTitle());
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!updateMode) {
                    title.setTitle(etContent.getText().toString());
                    title.setLearningSession(learningSession);
                    title.setCreatedBy(App.getCurrentUser().getId());

                    learningSession.createQuizTitle(title, new ServiceCallback<QuizTitle>() {
                        @Override
                        public void onComplete(QuizTitle data) {
                            title.copy(data);
                            displayInfoMessage("Quiz title saved successfully!");
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            displayErrorMessage(message);
                        }
                    });
                } else {
                    title.setTitle(etContent.getText().toString());
                    learningSession.updateQuizTitle(title, new ServiceCallback<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            displayInfoMessage("Quiz title updated successfully!");
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            displayErrorMessage(message);
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
