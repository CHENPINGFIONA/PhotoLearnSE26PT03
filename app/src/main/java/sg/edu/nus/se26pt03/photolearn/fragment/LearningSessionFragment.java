package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.se26pt03.photolearn.R;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_session, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFragment(R.id.vp_learningsession_pager, new LearningTitleListFragment(),"Titles");
        addFragment(R.id.vp_learningsession_pager, new QuizTitleListFragment(),"Quizzes");
        setupTabLayout(R.id.tab_learningsession_layout, R.id.vp_learningsession_pager);
    }

}
