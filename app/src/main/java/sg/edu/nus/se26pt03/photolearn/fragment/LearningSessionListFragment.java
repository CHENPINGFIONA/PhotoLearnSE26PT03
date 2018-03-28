package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TextInputLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Participant;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.databinding.DialogLearningsessionAccessBinding;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningSessionListAdapter;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private LearningSessionListAdapter learningSessionListAdapter;
    private List<LearningSession> learningSessions;
    private List<LearningSession> learningSessionsRaw;

    private SwipeRefreshLayout srf_learningsessionlist;
    private SearchView sv_learningsessionlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learning_session_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupControls();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void applyFilter() {
        Stream<LearningSession> learningSessionStream = learningSessionsRaw.stream();
        final String query = (sv_learningsessionlist == null ? "" : sv_learningsessionlist.getQuery().toString().toUpperCase());
        if (!query.isEmpty()) {
            learningSessionStream = learningSessionStream.filter(s -> (s.getCourseCode() + s.getCourseName() + s.getModuleNumber() + s.getModuleName()).toUpperCase().contains(query));
        }
            if(learningSessions == null) learningSessions = new ArrayList<LearningSession>();
            learningSessions.clear();
            learningSessions.addAll(learningSessionStream.collect(Collectors.toList()));
    }

    private void loadData() {
        srf_learningsessionlist.setRefreshing(true);
        App.getCurrentUser().getLearningSessions(new ServiceCallback<List<LearningSession>>() {
            @Override
            public void onComplete(List<LearningSession> data) {

                if (data.size() == 0) sv_learningsessionlist.setVisibility(View.GONE);
                else sv_learningsessionlist.setVisibility(View.VISIBLE);

                if(learningSessionsRaw == null) learningSessionsRaw = new ArrayList<LearningSession>();
                learningSessionsRaw.clear();
                learningSessionsRaw.addAll(data);
                applyFilter();
                refreshViews();
                srf_learningsessionlist.setRefreshing(false);
            }

            @Override
            public void onError(int code, String message, String details) {
                srf_learningsessionlist.setRefreshing(false);
                displayErrorMessage(message);
            }
        });
    }

    private void refreshViews() {
        if(learningSessionListAdapter.getLearningSessions() != learningSessions) {
            learningSessionListAdapter.setLearningSessions(learningSessions);

        }
        learningSessionListAdapter.notifyDataSetChanged();
        if (getView() != null) {
            if (learningSessionListAdapter.getLearningSessions().size() == 0) {
                getView().findViewById(R.id.tv_learningtitlelist_norecordfound).setVisibility(View.VISIBLE);
            } else {
                getView().findViewById(R.id.tv_learningtitlelist_norecordfound).setVisibility(View.GONE);
            }
        }
    }

    private void setupControls() {

        final RecyclerView recyclerView = getView().findViewById(R.id.rv_learningsessionlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        learningSessionListAdapter = new LearningSessionListAdapter(learningSessions);
        learningSessionListAdapter.setLearningSessionViewHolderClick( new LearningSessionListAdapter.LearningSessionViewHolderClick() {
            @Override
            public void onItemClick(LearningSessionListAdapter.LearningSessionViewHolder viewHolder) {
                if (!dismissSoftInput()) {
                    onLoad(learningSessions.get(viewHolder.getAdapterPosition()), null);
                }
            }
        });
        recyclerView.setAdapter(learningSessionListAdapter);

        if (App.getCurrentAppMode() == AppMode.TRAINER) {
            final SwipeController swipeController = new SwipeController(0, R.layout.partial_swipe_item) {
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
                            new AlertDialog.Builder(getContext())
                                .setTitle("Confirmation")
                                .setMessage("Are you sure you wanted to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        ((Trainer) App.getCurrentUser()).deleteLearningSession(learningSessionListAdapter.getLearningSessions().get(position), new ServiceCallback<Boolean>() {
                                            @Override
                                            public void onComplete(Boolean data) {
                                                if (data) {
                                                    displayInfoMessage("Learning session has been deleted successfully!");
                                                }
                                            }
                                            @Override
                                            public void onError(int code, String message, String details) {
                                                displayErrorMessage(message);
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                            break;
                        case "edit":
                            onEdit(learningSessionListAdapter.getLearningSessions().get(position), null);
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
        }
        srf_learningsessionlist =  getView().findViewById(R.id.srf_learningsesionlist);
        srf_learningsessionlist.setOnRefreshListener(this);
        srf_learningsessionlist.post(new Runnable() {
            @Override
            public void run() {
                srf_learningsessionlist.setRefreshing(true);
                loadData();
            }
        });

        sv_learningsessionlist = getView().findViewById(R.id.sv_learningsessionlist);
        sv_learningsessionlist.setQueryHint("Filter by keywords");

        SearchView sv_learningsessionlist =  getView().findViewById(R.id.sv_learningsessionlist);
        sv_learningsessionlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SearchView) view).setIconified(false);
            }
        });
        sv_learningsessionlist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ((RecyclerView) getView().findViewById(R.id.rv_learningsessionlist)).setOnTouchListener((v, event) -> {
            return dismissSoftInput();
        });

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_learningsessionlist);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getCurrentAppMode() == AppMode.PARTICIPENT) {
                    LayoutInflater li = LayoutInflater.from(getContext());
//                    View viewAccessLearningSession = li.inflate(R.layout.dialog_learningsession_access, null);

//                    Dialog dialog = new Dialog(getContext());
//                    dialog.getWindow();
//                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    LearningSessionA termsBinding = LayoutTermsBinding
//                            .inflate(LayoutInflater.from(context), (ViewGroup) binding.getRoot(), false);
//
//
//                    LayoutTermsBinding termsBinding;
//
//                    dialog.setContentView(R.layout.layout_terms);
//                    dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                    dialog.show();


                    DialogLearningsessionAccessBinding dialogLearningsessionAccessBinding = DialogLearningsessionAccessBinding.inflate(LayoutInflater.from(getContext()),null, false);

                    AlertDialog ad_learningsessionaccess = new AlertDialog.Builder(getContext())
                            .setView(dialogLearningsessionAccessBinding.getRoot())
                            .setTitle("Learning Session Access")
                            .setPositiveButton("Add",null)
                            .setNegativeButton("Cancel",null)
                            .create();

                    EditText et_learningsessionid = (EditText) dialogLearningsessionAccessBinding.getRoot().findViewById(R.id.et_learningsessionid);
                    TextInputLayout til_learingsessionid = (TextInputLayout) dialogLearningsessionAccessBinding.getRoot().findViewById(R.id.til_learningsessionid);
                    ad_learningsessionaccess.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {

                            Button button = ad_learningsessionaccess.getButton(AlertDialog.BUTTON_POSITIVE);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((Participant) App.getCurrentUser()).accessLearnigSession(et_learningsessionid.getText().toString(), new ServiceCallback<Boolean>() {
                                        @Override
                                        public void onComplete(Boolean data) {
                                            ad_learningsessionaccess.dismiss();
                                            displayInfoMessage("Access to learning session successfully");
                                            loadData();
                                        }
                                        @Override
                                        public void onError(int code, String message, String details) {
                                            til_learingsessionid.setError(message);
                                            til_learingsessionid.setErrorEnabled(true);
                                        }
                                    });
                                }
                            });
                        }
                    });
                    ad_learningsessionaccess.show();
                }
                else {
                    onCreate(new LearningSession(), null);
                }
            }
        });
    }

    private boolean dismissSoftInput() {
     if (sv_learningsessionlist.hasFocus()) {
         sv_learningsessionlist.clearFocus();
//         hideSoftInput(sv_learningsessionlist.getWindowToken());
        return true;
     }
     return false;
    }
}
