package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizItemFragment;

/**
 * Created by c.banisetty on 3/14/2018.
 */

public class QuizItemFragmentPageAdapter extends FragmentStatePagerAdapter {
    public List<Item> getQuizItemList() {
        return quizItemList;
    }

    public void setQuizItemList(List<Item> quizItemList) {
        this.quizItemList = quizItemList;
    }

    private List<Item> quizItemList = null;
    private QuizTitle quizTitle;

    public QuizItemFragmentPageAdapter(FragmentManager fm, QuizTitle quizTitle, List<Item> items) {
        super(fm);
        this.quizItemList = items==null?new ArrayList<Item>():items;
        this.quizTitle = quizTitle;

    }


    @Override
    public Fragment getItem(int position) {
        return QuizItemFragment.create(position, (QuizItem) this.quizItemList.get(position));
    }

    public QuizItem getQuizItemByPosition(int position) {
        if (position <= getCount()) {
            return (QuizItem) this.quizItemList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.quizItemList.size();
    }
}
