package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizItemFragment;

/**
 * Created by c.banisetty on 3/14/2018.
 */

public class QuizItemFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<QuizItem> quizItemList=null;
    private String titleId;

    public QuizItemFragmentPageAdapter(FragmentManager fm, String titleId) {
        super(fm);
        this.quizItemList = new ArrayList<QuizItem>();
        this.titleId=titleId;
        QuizItem item=new QuizItem();
        item.setContent(" Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage");
        item.setPhotoURL("http://i63.tinypic.com/2yjzcrr.jpg");
        item.setCoordinate(new Coordinate(37.137089, -93.276932) );
        QuizItem item2=new QuizItem();
        item2.setContent("Content for basic usage 23");
        item2.setPhotoURL("http://i63.tinypic.com/rrmh4y.jpg");
        item.setCoordinate(new Coordinate(51.507351, -0.127758) );
        QuizItem item3=new QuizItem();
        item3.setContent("Content for basic usage 24");
        item3.setPhotoURL("http://i64.tinypic.com/mv3kb7.jpg");
        item.setCoordinate(new Coordinate(26.820553, 30.802498) );
        this.quizItemList.add(item);
        this.quizItemList.add(item2);
        this.quizItemList.add(item3);
    }


    @Override
    public Fragment getItem(int position) {
        return QuizItemFragment.create(position,this.quizItemList.get(position));
    }
    public QuizItem getLearningItemByPosition(int position){
        if(position <= getCount()){
            return this.quizItemList.get(position);
        }
        return null;
    }
    @Override
    public int getCount() {
        return this.quizItemList.size();
    }
}
