package sg.edu.nus.se26pt03.photolearn.fragment;


import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.Action;
import sg.edu.nus.se26pt03.photolearn.application.AppFragment;


/**
 * Created by MyatMin on 08/3/18.
 */
public class HomeFragment extends AppFragment implements AppFragment.LearningEventListener {

    private static final String ARG_TITLE = "Title";
    private  String mTitle;

    public HomeFragment() {}
    public static HomeFragment newInstance(String Title) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, Title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
        setTitle(mTitle, false);
        setFragment(R.id.fl_main, new LearningSessionListFragment());
    }

    public void setTitle(String Title, boolean BackStackInd) {
        Toolbar toolbar = this.getView().findViewById(R.id.toolbar);
        toolbar.setTitle(Title);
        if (BackStackInd) toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onLearningSessionReaction(Action action) {
        switch (action) {
            case SELECTED:
                setFragment(R.id.fl_main, new LearningSessionFragment());
        }
        mLearningEventListener.onLearningSessionReaction(action);
    }

    @Override
    public void onLearningTitleReaction(Action action) {

    }

    @Override
    public void onLearningItemReaction(Action action) {

    }

    @Override
    public void onQuizTitleReaction(Action action) {

    }

    @Override
    public void onQuizItemReaction(Action action) {

    }

    @Override
    public void onQuizAnswerReaction(Action action) {

    }
}
