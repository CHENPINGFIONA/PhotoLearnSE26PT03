package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sg.edu.nus.se26pt03.photolearn.fragment.LearningTitleListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizTitleListFragment;

/**
 * Created by chen ping on 11/3/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LearningTitleListFragment();
            case 1:
            default:
                return new QuizTitleListFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
