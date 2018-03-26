package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.ItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;


/**
 * Created by MyatMin on 08/3/18.
 */
public class QuizItemListFragment extends BaseFragment {

    public static String SharedPreferences_Access_Mode= "ACCESSMODE";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private TextView tvEmpty;
    private FloatingActionButton Add;
    private Dialog dialog;
    private int role;
    private int mode;
    private String sessionId;
    private QuizTitle quizTitle;
    private String userId;
    private ImageView popupimagebutton;

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
        quizTitle = (QuizTitle) getArguments().getSerializable(ConstHelper.REF_QUIZ_TITLES);
        sessionId = "1";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_Access_Mode, AccessMode.EDIT);
        role = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, UserRole.toInt(UserRole.PARTICIPENT));
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupControls();

    }

    private void setupControls() {
        popupimagebutton.setVisibility((mode == AccessMode.EDIT && role == UserRole.toInt(UserRole.PARTICIPENT)) ? View.VISIBLE : View.GONE);
        Add.setVisibility((mode == AccessMode.EDIT && role == UserRole.toInt(UserRole.PARTICIPENT)) ? View.VISIBLE : View.GONE);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(new QuizItem(quizTitle),    null);
            }
        });
        popupimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= popupSetup(popupimagebutton);
                popupMenu.show();
            }
        });
    }

    private void setupViews() {
        tvEmpty = (TextView) getView().findViewById(R.id.tv_itemempty_value);
        Add = (FloatingActionButton) getView().findViewById(R.id.fab_addlearningitem);
        // Delete= (FloatingActionButton) findViewById(R.id.DeleteItemfloatingActionButton);
        // Edit=(FloatingActionButton) findViewById(R.id.EditItemfloatingActionButton);

        popupimagebutton = (ImageView) getView().findViewById(R.id.img_popupmenu);


        mPager = (ViewPager) getView().findViewById(R.id.vp_quizitemlistt);
        mPagerAdapter = new QuizItemFragmentPageAdapter(getFragmentManager(), quizTitle);
        mPager.setAdapter(mPagerAdapter);
    }

    public  PopupMenu popupSetup(ImageView imageView)
    {
        PopupMenu popupMenu=new PopupMenu(getContext(),imageView);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_learning_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean result=false;
                switch (menuItem.getItemId()){
                    case R.id.menu_delete:
                        new AlertDialog.Builder(getContext())
                                .setTitle("Title")
                                .setMessage("Are you sure you wanted to delete this item?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                       return;
                                    }})
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       return;
                                    }
                                }).show();
                        result=true;
                        break;
                    case R.id.menu_edit:
                        onEdit(((ItemFragmentPageAdapter)mPagerAdapter).getLearningItemByPosition(mPager.getCurrentItem()), null);
                        result=true;
                        break;
                }
                return result;
            }
        });
        return  popupMenu;

    }
    @Override
    public void onResume() {
        super.onResume();
        tvEmpty.setVisibility(mPagerAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
       // Delete.setVisibility(mPagerAdapter.getCount() == 0 ? View.INVISIBLE : View.VISIBLE);
       // Edit.setVisibility(mPagerAdapter.getCount() == 0 ? View.INVISIBLE : View.VISIBLE);
    }


}
