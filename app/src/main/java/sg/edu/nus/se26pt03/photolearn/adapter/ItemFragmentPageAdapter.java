package sg.edu.nus.se26pt03.photolearn.adapter;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemFragment;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.service.LearningItemService;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by c.banisetty on 3/14/2018.
 */

public class ItemFragmentPageAdapter extends FragmentStatePagerAdapter {
    public List<Item> getLearningItemList() {
        return learningItemList;
    }

    public void setLearningItemList(List<Item> learningItemList) {
        this.learningItemList = learningItemList;
    }

    private List<Item> learningItemList = null;
    private LearningTitle title;
    private LearningItemService learningItemService = new LearningItemService();


    public ItemFragmentPageAdapter(FragmentManager fm, LearningTitle title, List<Item> learningItemList) {
        super(fm);
        this.learningItemList = learningItemList != null ? learningItemList : new ArrayList<Item>();

        //loadList(titleId);
        this.title = title;

        /*LearningItem item = new LearningItem(title);
        item.setContent(" Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage Content for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usageContent for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage \n Content for basic usage");
        item.setPhotoURL("http://i63.tinypic.com/2yjzcrr.jpg");
        item.setCoordinate(new Coordinate(37.137089, -93.276932));
        LearningItem item2 = new LearningItem(title);
        item2.setContent("Content for basic usage 23");
        item2.setPhotoURL("http://i63.tinypic.com/rrmh4y.jpg");
        item.setCoordinate(new Coordinate(51.507351, -0.127758));
        LearningItem item3 = new LearningItem(title);
        item3.setContent("Content for basic usage 24");
        item3.setPhotoURL("http://i64.tinypic.com/mv3kb7.jpg");
        item.setCoordinate(new Coordinate(26.820553, 30.802498));
        this.learningItemList.add(item);
        this.learningItemList.add(item2);
        this.learningItemList.add(item3);*/
    }


    @Override
    public Fragment getItem(int position) {
        return LearningItemFragment.create(position, (LearningItem) this.learningItemList.get(position));
    }

    public LearningItem getLearningItemByPosition(int position) {
        if (position <= getCount()) {
            return (LearningItem) this.learningItemList.get(position);
        }
        return null;
    }


    @Override
    public int getCount() {
        return this.learningItemList.size();
    }
}
