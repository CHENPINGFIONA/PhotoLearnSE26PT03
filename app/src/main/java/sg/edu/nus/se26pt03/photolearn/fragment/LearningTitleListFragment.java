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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

public class LearningTitleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private LearningTitleListAdapter learningTitleListAdapter;
    private LearningTitleService learningTitleService;
    private List<LearningTitle> learningTitles;
    private List<LearningTitle> learningTitlesOrigianl;

    private SwipeRefreshLayout srf_learningtitlelist;
    private SearchView sv_learningtitlelist;

    private LearningSession learningSession;
    private boolean updateMode;

    public static LearningTitleListFragment newInstance(LearningSession learningSession) {
        LearningTitleListFragment learningTitleListFragment = new LearningTitleListFragment();
        Bundle args = new Bundle();
        args.putSerializable("learningSession", learningSession);
        learningTitleListFragment.setArguments(args);
        return learningTitleListFragment;
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
        learningTitleService = new LearningTitleService(learningSession);
        learningTitles = new ArrayList<>();
        learningTitlesOrigianl = learningSession.getLearningTitles();
        applyFilter();

        learningTitleListAdapter = new LearningTitleListAdapter(learningTitles, new LearningTitleListAdapter.LearningTitleViewHolderClick() {
            @Override
            public void onItemClick(LearningTitleListAdapter.LearningTitleViewHolder viewHolder) {
                onLoad(learningTitles.get(viewHolder.getAdapterPosition()), null);
            }
        });
    }

    private void refreshData() {
        srf_learningtitlelist.setRefreshing(true);
        learningTitleService.getAllByKeyValue("learningSessionId", learningSession.getId(), new ServiceCallback<List<LearningTitle>>() {
            @Override
            public void onComplete(List<LearningTitle> data) {
                learningSession.removeAllLearningTitle();
                learningSession.addLearningTitles(data);

                applyFilter();
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
        learningTitleListAdapter.notifyDataSetChanged();
        if (learningTitleListAdapter.learningTitleList.size() == 0)
            getView().findViewById(R.id.tv_learningtitlelist_hint).setVisibility(View.VISIBLE);

    }

    private void setupViews() {
        sv_learningtitlelist = getView().findViewById(R.id.sv_title);
        sv_learningtitlelist.setVisibility(App.getCurrentAccessMode() == AccessMode.VIEW && App.getCurrentAppMode() == AppMode.PARTICIPENT ? View.VISIBLE : View.GONE);
        sv_learningtitlelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SearchView) view).setIconified(false);
            }
        });
        sv_learningtitlelist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilter();
                refreshViews();
                return false;
            }
        });
    }

    private void setupControls() {
        RecyclerView recyclerView = getView().findViewById(R.id.rv_learning_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(learningTitleListAdapter);
        final SwipeController swipeController = new SwipeController(0, (App.getCurrentAccessMode() == AccessMode.EDIT ? R.layout.partial_swipe_item : 0)) {
            @Override
            public void onRevealInflated(View view, int position) {
                if (view instanceof LinearLayout) {
                    LearningTitle title = learningTitleListAdapter.learningTitleList.get(position);
                    LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
                    linearLayout.findViewWithTag("edit").setVisibility(App.getCurrentAppMode()== AppMode.TRAINER ? View.VISIBLE : View.GONE);
                    linearLayout.findViewWithTag("delete").setVisibility(App.getCurrentAppMode()==AppMode.TRAINER ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onClicked(Object tag, final int position) {
                final LearningTitle title = learningTitleListAdapter.learningTitleList.get(position);
                switch (tag.toString()) {
                    case "delete":
                        new AlertDialog.Builder(getContext())
                                .setTitle("Title")
                                .setMessage("Are you sure you wanted to delete this learning title?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        learningSession.deleteLearningTitle(title.getId());
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
        floatingActionButton.setVisibility((App.getCurrentAccessMode() == AccessMode.EDIT && App.getCurrentAppMode() == AppMode.PARTICIPENT) ? View.VISIBLE : View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                showDialogue(new LearningTitle());
            }
        });
    }

    private void showDialogue(final LearningTitle title) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.learning_title);

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

                    learningSession.createLearningTitle(title, new ServiceCallback<LearningTitle>() {
                        @Override
                        public void onComplete(LearningTitle data) {
                            title.copy(data);
                            displayInfoMessage("Learning title saved successfully!");
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            displayErrorMessage(message);
                        }
                    });
                } else {
                    title.setTitle(etContent.getText().toString());
                    learningSession.updateLearningTitle(title, new ServiceCallback<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            displayInfoMessage("Learning title updated successfully!");
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

    private void applyFilter() {
        Stream<LearningTitle> learningTitleStream = learningTitlesOrigianl.stream();
        final String query = (sv_learningtitlelist == null ? "" : sv_learningtitlelist.getQuery().toString().toUpperCase());

        Boolean isEditMode = App.getCurrentAccessMode() == AccessMode.EDIT;

        learningTitleStream = learningTitleStream.filter(s -> (s.getTitle() + s.getCreatedBy()).toUpperCase().contains(query)
                && (!isEditMode || s.getCreatedBy().equals(App.getCurrentUser().getId())));
        learningTitles.clear();
        learningTitles.addAll(learningTitleStream.collect(Collectors.toList()));
    }
}
