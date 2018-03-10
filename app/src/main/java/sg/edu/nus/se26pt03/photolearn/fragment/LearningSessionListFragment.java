package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.AppFragment;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionListFragment extends AppFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_session_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_addlearningsession);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLearningEventListener.onLearningSessionReaction(App.Action.SELECTED);
            }
        });
    }
}
