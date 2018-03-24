package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.ItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.LearningItemService;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;


/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningItemListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static String SharedPreferences_Access_Mode = "ACCESSMODE";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private TextView tvEmpty;
    private FloatingActionButton Add;
    private Dialog dialog;
    private int role;
    private int mode;
    private String sessionId;
    private String userId;
    private ImageView popupimagebutton;
    private LearningTitle learningTitle;
    private List<Item> learningItemList = null;
    private LearningItemService learningItemService = new LearningItemService();
    private SwipeRefreshLayout srf_learningItemList;


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
        View view = inflater.inflate(R.layout.fragment_learning_item_list, container, false);
        //  super.onCreateView(inflater, container, savedInstanceState);
        //setContentView(R.layout.activity_view_page_with_fragment);
        learningTitle = (LearningTitle) getArguments().getSerializable(ConstHelper.REF_LEARNING_TITLES);
        sessionId = "1";
        //titleId="-L88Kii8Oc5tSrTBxNaW";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_Access_Mode, AccessMode.toInt(AccessMode.EDIT));
        role = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, UserRole.toInt(UserRole.PARTICIPENT));
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupControls();
        //setupViews();


    }

    private void setupControls() {


        tvEmpty = (TextView) getView().findViewById(R.id.tv_itemempty_value);
        Add = (FloatingActionButton) getView().findViewById(R.id.fab_addlearningitem);
        popupimagebutton = (ImageView) getView().findViewById(R.id.img_popupmenu);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(new LearningItem(learningTitle), null);
            }
        });
        popupimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = popupSetup(popupimagebutton);
                popupMenu.show();
            }
        });
        mPager = (ViewPager) getView().findViewById(R.id.vp_learningitem);
        mPagerAdapter = new ItemFragmentPageAdapter(getChildFragmentManager(), learningTitle,this.learningItemList);
        mPager.setAdapter(mPagerAdapter);

        srf_learningItemList =  getView().findViewById(R.id.srf_learningItemList);
        srf_learningItemList.setOnRefreshListener(this);
        srf_learningItemList.post(new Runnable() {
            @Override
            public void run() {
                srf_learningItemList.setRefreshing(true);
                loadList();
            }
        });
    }

    private void setupViews() {
        tvEmpty.setVisibility(mPagerAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        popupimagebutton.setVisibility(mPagerAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);

/*
        mPagerAdapter = new ItemFragmentPageAdapter(getChildFragmentManager(), learningTitle,this.learningItemList);

        mPager.setAdapter(mPagerAdapter);

        loadList();*/
    }

    public PopupMenu popupSetup(ImageView imageView) {
        PopupMenu popupMenu = new PopupMenu(getContext(), imageView);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_learning_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean result = false;
                switch (menuItem.getItemId()) {
                    case R.id.menu_delete:
                        new AlertDialog.Builder(getContext())
                                .setTitle("Title")
                                .setMessage("Are you sure you wanted to delete this item?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        return;
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }).show();
                        result = true;
                        break;
                    case R.id.menu_edit:
                        onEdit(((ItemFragmentPageAdapter) mPagerAdapter).getLearningItemByPosition(mPager.getCurrentItem()), null);
                        result = true;
                        break;
                }
                return result;
            }
        });
        return popupMenu;

    }

    @Override
    public void onResume() {
        super.onResume();
       // loadList();
       // setupViews();
    }


    private void loadList() {
        srf_learningItemList.setRefreshing(true);
        this.learningTitle.getItems(new ServiceCallback<List<Item>>() {

            @Override
            public void onComplete(List<Item> data) {

                if (data != null) {
                    if(learningItemList !=null )
                        learningItemList.clear();
                    LearningItemListFragment.this.learningItemList = data;
                    ((ItemFragmentPageAdapter)mPagerAdapter).setLearningItemList(data);
                    mPagerAdapter.notifyDataSetChanged();
                    setupViews();
                    srf_learningItemList.setRefreshing(false);
                }
            }
            @Override
            public void onError(int code, String message, String details) {
                Log.w("ERROR", code + "-" + message + "-" + details);

            }
        });
    }


    @Override
    public void onRefresh() {
        loadList();
    }


}
