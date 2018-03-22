package sg.edu.nus.se26pt03.photolearn.adapter;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemFragment;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.service.LearningItemService;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by c.banisetty on 3/14/2018.
 */

public class ItemFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<LearningItem> learningItemList=null;
    private String titleId;
    private LearningItemService learningItemService = new LearningItemService();


    public ItemFragmentPageAdapter(FragmentManager fm, String titleId) {
        super(fm);
        this.learningItemList = new ArrayList<LearningItem>();

        //loadList(titleId);
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

    void loadList(String titleId){
        this.learningItemList.clear();
        learningItemService.getAllByLearningTitleId(titleId, new ServiceCallback<List<LearningItem>>() {
            @Override
            public void onComplete(List<LearningItem> data) {
                if(data!=null)
                    ItemFragmentPageAdapter.this.learningItemList=data;
            }


            @Override
            public void onError(int code, String message, String details) {
                Log.w("ERROR",code+"-"+message+"-"+details);

            }
        });
    }
    @Override
    public Fragment getItem(int position) {
        return LearningItemFragment.create(position,this.learningItemList.get(position));
    }
    public LearningItem getLearningItemByPosition(int position){
        if(position <= getCount()){
            return this.learningItemList.get(position);
        }
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        loadList(titleId);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.learningItemList.size();
    }
}
