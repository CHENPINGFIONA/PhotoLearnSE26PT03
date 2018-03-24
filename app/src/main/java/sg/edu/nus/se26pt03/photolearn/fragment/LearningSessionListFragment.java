package sg.edu.nus.se26pt03.photolearn.fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningSessionListAdapter;
import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private LearningSessionListAdapter learningSessionListAdapter;
    private LearningSessionService learningSessionService;
    private List<LearningSession> learningSessions;

    private SwipeRefreshLayout srf_learningsessionlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learning_session_list, container, false);
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
        learningSessionService = new LearningSessionService();
        if (App.currentAppMode == AppMode.TRAINER) {
            learningSessions = ((Trainer) App.currentUser).getLearningSessions();
        }

        learningSessionListAdapter = new LearningSessionListAdapter(learningSessions, new LearningSessionListAdapter.LearningSessionViewHolderClick() {
            @Override
            public void onItemClick(LearningSessionListAdapter.LearningSessionViewHolder viewHolder) {
                onLoad(learningSessions.get(viewHolder.getAdapterPosition()), null);
            }
        });
    }

    private void refreshData() {
        srf_learningsessionlist.setRefreshing(true);
        learningSessionService.getAllByKeyValue("createdBy", App.currentUser.getId() ,new ServiceCallback<List<LearningSession>>() {
            @Override
            public void onComplete(List<LearningSession> data) {
                ((Trainer) App.currentUser).removeAllLearningSesson();
                ((Trainer) App.currentUser).addLearningSession(data);
                learningSessions = ((Trainer) App.currentUser).getLearningSessions();
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
        learningSessionListAdapter.notifyDataSetChanged();
        if (learningSessionListAdapter.learningSessionList.size() == 0)
            getView().findViewById(R.id.tv_learningsessionlist_hint).setVisibility(View.VISIBLE);

    }

    private void setupViews() {
        SearchView sv_learningsessionlist =  getView().findViewById(R.id.sv_learningsessionlist);
        switch (App.currentAppMode) {
            case TRAINER:
                //sv_learningsessionlist.setVisibility(View.GONE);
                break;
            case PARTICIPENT:
                getView().findViewById(R.id.fab_learningsessionlist).setVisibility(View.GONE);
                break;
        }


    }

    private void setupControls() {
        final RecyclerView recyclerView = getView().findViewById(R.id.rv_learningsessionlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(learningSessionListAdapter);
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
                        learningSessionListAdapter.learningSessionList.remove(position);
                        learningSessionListAdapter.notifyItemRemoved(position);
                        learningSessionListAdapter.notifyItemRangeChanged(position, learningSessionListAdapter.getItemCount());
                        break;
                    case "edit":
                        onEdit(learningSessionListAdapter.learningSessionList.get(position), null);
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

        srf_learningsessionlist =  getView().findViewById(R.id.srf_learningsesionlist);
        srf_learningsessionlist.setOnRefreshListener(this);
        srf_learningsessionlist.post(new Runnable() {
            @Override
            public void run() {
                srf_learningsessionlist.setRefreshing(true);
                refreshData();
            }
        });

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_learningsessionlist);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getCurrentAppMode() == AppMode.PARTICIPENT) {

                }
                else {
                    onCreate(new LearningSession(), null);
                }
            }
        });
    }
}
