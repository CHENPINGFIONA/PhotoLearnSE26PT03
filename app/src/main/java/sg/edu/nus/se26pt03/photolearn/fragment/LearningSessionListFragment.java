package sg.edu.nus.se26pt03.photolearn.fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


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
import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.database.LearningSessionRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningSessionListAdapter;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionListFragment extends BaseFragment {
    private LearningSessionListAdapter learningSessionListAdapter;
    private LearningSessionRepo a = new LearningSessionRepo();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_session_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadLearingSessionList();
        setupViews();
        setupControls();
    }
    private  void loadLearingSessionList() {
        final List<LearningSession> learningSessionList = new ArrayList<LearningSession>();
        if (App.currentAppMode == AppMode.TRAINER)
            try {
                for(int i=1; i <= 100; i++) {
                    LearningSession ls = new LearningSession();
                    ls.setCourseCode("IOT");
                    ls.setCourseName("Internet of Things");
                    ls.setModuleName("Overview");
                    ls.setModuleNumber(i);

                    ls.setCourseDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-01"));
                    learningSessionList.add(ls);
                }
            }
            catch (ParseException e) {

            }
        learningSessionListAdapter = new LearningSessionListAdapter(learningSessionList, new LearningSessionListAdapter.LearningSessionViewHolderClick() {
            @Override
            public void onItemClick(LearningSessionListAdapter.LearningSessionViewHolder viewHolder) {
                onLoad(learningSessionList.get(viewHolder.getAdapterPosition()), null);
            }
        });
    }

    private void setupViews() {
        // Write a message to the database
//        LearningSessionDAO l = new LearningSessionDAO();
//        l.setCourseCode("iot");
//        l.setCourseName("Internet of Things");
//        LearningSessionDAO l2 = new LearningSessionDAO();
//        l2.setCourseCode("iot");
//        l2.setCourseName("Internet of Things 2");
//        l.setCourseName("Internet of Things");
//        LearningSessionDAO l3 = new LearningSessionDAO();
//        l3.setCourseCode("iot");
//        l3.setCourseName("Internet of Things 3");
//        List <LearningSessionDAO> m = new ArrayList<LearningSessionDAO>();
//        m.add(l);
//        m.add(l2);
//        l3.setNested(m);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("LearningSessions");
//        String a = myRef.push().getKey();
//        myRef.child(a).setValue(l3);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                LearningSessionDAO value = dataSnapshot.getValue(LearningSessionDAO.class);
//                Log.d("dd", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("ddd", "Failed to read value.", error.toException());
//            }
//        });
        a.getAll(new RepoCallback<LearningSessionDAO>() {
            @Override
            public void onRecieved(List<LearningSessionDAO> data) {
                Log.d("aa", data.toString());
            }
        });

        if (learningSessionListAdapter.learningSessionList.size() >0) getView().findViewById(R.id.tv_learningsessionlist_hint).setVisibility(View.GONE);
        switch (App.currentAppMode) {
            case TRAINER:
                getView().findViewById(R.id.sv_learningsessionlist).setVisibility(View.GONE);
                break;
            case PARTICIPENT:
                getView().findViewById(R.id.fab_learningsessionlist).setVisibility(View.GONE);
                break;
        }
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

    }
    private void setupControls() {
        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_learningsessionlist);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(new LearningSession(), null);
            }
        });
    }
}
