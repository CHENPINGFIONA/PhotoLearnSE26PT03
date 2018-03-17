package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.ItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;


/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningItemListFragment extends BaseFragment {

    public static String SharedPreferences_Access_Mode= "ACCESSMODE";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String sessionId;
    private Dialog dialog;
    private String mode;
    private TextView tvEmpty;
    private String userId;
    private FloatingActionButton Add,Edit,Delete;

//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_learning_item_list, container, false);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_learning_item_list, container, false);
      //  super.onCreateView(inflater, container, savedInstanceState);
        //setContentView(R.layout.activity_view_page_with_fragment);
        sessionId = "1";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SharedPreferences_Access_Mode, App.currentAccessMode.toString());
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, App.currentUser.toString());
        tvEmpty = (TextView) view.findViewById(R.id.tv_itemempty_value);
       // Add = (FloatingActionButton) findViewById(R.id.NewItemfloatingActionButton);
       // Delete= (FloatingActionButton) findViewById(R.id.DeleteItemfloatingActionButton);
       // Edit=(FloatingActionButton) findViewById(R.id.EditItemfloatingActionButton);

        mPager = (ViewPager) view.findViewById(R.id.vp_learningitem);
        mPagerAdapter = new ItemFragmentPageAdapter(getFragmentManager(), "1");
        mPager.setAdapter(mPagerAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        tvEmpty.setVisibility(mPagerAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
       // Delete.setVisibility(mPagerAdapter.getCount() == 0 ? View.INVISIBLE : View.VISIBLE);
       // Edit.setVisibility(mPagerAdapter.getCount() == 0 ? View.INVISIBLE : View.VISIBLE);
    }


}
