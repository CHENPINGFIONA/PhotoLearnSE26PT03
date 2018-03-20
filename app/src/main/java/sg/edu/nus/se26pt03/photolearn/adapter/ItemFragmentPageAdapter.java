package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemFragment;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
/**
 * Created by c.banisetty on 3/14/2018.
 */

public class ItemFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<LearningItem> learningItemList=null;
    private String titleId;

    public ItemFragmentPageAdapter(FragmentManager fm, String titleId) {
        super(fm);
        this.learningItemList = new ArrayList<LearningItem>();
        this.titleId=titleId;
        LearningItem item=new LearningItem();
        item.setContent(" Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage");
        item.setPhotoURL("http://i63.tinypic.com/2yjzcrr.jpg");
        item.setCoordinate(new Coordinate(37.137089, -93.276932) );
        LearningItem item2=new LearningItem();
        item2.setContent("Content for basic usage 23");
        item2.setPhotoURL("http://i63.tinypic.com/rrmh4y.jpg");
        item.setCoordinate(new Coordinate(51.507351, -0.127758) );
        LearningItem item3=new LearningItem();
        item3.setContent("Content for basic usage 24");
        item3.setPhotoURL("http://i64.tinypic.com/mv3kb7.jpg");
        item.setCoordinate(new Coordinate(26.820553, 30.802498) );
        this.learningItemList.add(item);
        this.learningItemList.add(item2);
        this.learningItemList.add(item3);
    }


    @Override
    public Fragment getItem(int position) {
        return LearningItemFragment.create(position,this.learningItemList.get(position));
    }

    @Override
    public int getCount() {
        return this.learningItemList.size();
    }
}
