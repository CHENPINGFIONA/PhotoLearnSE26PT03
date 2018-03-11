package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sg.edu.nus.se26pt03.photolearn.fragment.LearningTitleListFragment;

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
        return new LearningTitleListFragment();
//        LearningTitleListFragment learningTitleListFragment = new LearningTitleListFragment();
//        return learningTitleListFragment;
//        switch (position) {
//            case 0:
//                LearningTitleListFragment learningTitleListFragment = new LearningTitleListFragment();
//                return learningTitleListFragment;
//            case 1:
//            default:
//                LearningTitleListFragment learningTitleListFragment = new LearningTitleListFragment();
//                return learningTitleListFragment;
//        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
