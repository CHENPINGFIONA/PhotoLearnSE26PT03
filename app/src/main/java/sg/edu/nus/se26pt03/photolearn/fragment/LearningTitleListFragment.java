package sg.edu.nus.se26pt03.photolearn.fragment;

import android.app.Dialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningSessionListAdapter;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class LearningTitleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private LearningTitleListAdapter learningTitleListAdapter;
    private LearningTitleService learningTitleService;
    private List<LearningTitle> learningTitles = null;

    private SwipeRefreshLayout srf_learningtitlelist;

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
        return inflater.inflate(R.layout.fragment_learning_title_list, container, false);
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
        learningTitleService = new LearningTitleService();
        learningTitles = learningSession.getLearningTitles();
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
                // ((Trainer) App.currentUser).removeAllLearningSesson();
                learningSession.removeAllLearningTitle();
                learningSession.addLearningTitle(data);

                learningTitles = learningSession.getLearningTitles();
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
        SearchView svSearchView = getView().findViewById(R.id.sv_learningtitle);
        svSearchView.setVisibility(App.getCurrentAccessMode() == AccessMode.VIEW ? View.VISIBLE : View.GONE);
        svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                callSearch(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {
                callSearch(text);
                return true;
            }

            private void callSearch(String text) {
                //loadLearningTitleList(text);
            }
        });
    }

    private void setupControls() {
        RecyclerView recyclerView = getView().findViewById(R.id.rv_learning_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(learningTitleListAdapter);
        final SwipeController swipeController = new SwipeController(0, (App.currentAccessMode == AccessMode.EDIT ? R.layout.partial_swipe_item : 0)) {
            @Override
            public void onRevealInflated(View view, int position) {
                if (view instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
//                        linearLayout.findViewWithTag("edit").setVisibility(View.GONE);
//                        linearLayout.findViewWithTag("delete").setVisibility(View.GONE);
                }
            }

            @Override
            public void onClicked(Object tag, int position) {
                switch (tag.toString()) {
                    case "delete":
                        learningTitleListAdapter.learningTitleList.remove(position);
                        learningTitleListAdapter.notifyItemRemoved(position);
                        learningTitleListAdapter.notifyItemRangeChanged(position, learningTitleListAdapter.getItemCount());
                        break;
                    case "edit":
                        onEdit(learningTitleListAdapter.learningTitleList.get(position), null);
                        break;
                }
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        // floatingActionButton.setVisibility((mode == AccessMode.toInt(AccessMode.EDIT) && role == UserRole.toInt(UserRole.PARTICIPENT)) ? View.VISIBLE : View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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
                            displayInfoMessage("Learning session saved successfully!");
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
                            displayInfoMessage("Learning session updated successfully!");
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
