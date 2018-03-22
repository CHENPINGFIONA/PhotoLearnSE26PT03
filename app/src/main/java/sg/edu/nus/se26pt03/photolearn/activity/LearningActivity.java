package sg.edu.nus.se26pt03.photolearn.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.fragment.LearnigItemDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionListFragment;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class LearningActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity.super.onBackPressed();
            }
        });
        setFragment(R.id.fl_main, new LearningSessionListFragment(),"Welcome " + (App.currentAppMode == AppMode.TRAINER? "Trainer" : "Participant") + "!" ,false,null, null);
        findViewById(R.id.btn_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.currentAppMode == AppMode.TRAINER) App.currentAppMode = AppMode.PARTICIPENT;
                else if (App.currentAppMode == AppMode.PARTICIPENT) App.currentAppMode = AppMode.TRAINER;
                //onModeChange(App.currentAppMode, null);
                onLoad(new LearningTitle(), null);
                //onLoad(new LearningSession(), null);
            }
        });
    }

    @Override
    public void onModeChange(AppMode appMode, UserActionCallback callback) {
        super.onModeChange(appMode, new UserActionCallback(){
            @Override
            public void onPass() {
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onLoad(final LearningSession learningSession, UserActionCallback callback) {
        super.onLoad(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, new LearningSessionFragment(), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
            }
        });
    }

    @Override
    public void onEdit(final LearningSession learningSession, UserActionCallback callback) {
        super.onCreate(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, new LearningSessionDetailFragment(), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
            }
        });
    }




    @Override
    public void onCreate(final LearningSession learningSession, UserActionCallback callback) {
        super.onEdit(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, new LearningSessionDetailFragment(), "New Learning Session",true, null, null);
            }
        });
    }

    /*
    * Learning Items Declarations Start Here
    *
    * */
    @Override
    public void onLoad(LearningTitle learningTitle, UserActionCallback callback) {
        super.onLoad(learningTitle, new UserActionCallback(){
            @Override
            public void onPass() {

                setFragment(R.id.fl_main, new LearningItemListFragment(),"Learning Title 1", true, null, null);
            }
        });
    }
    @Override
    public void onCreate(final LearningItem learningItem, UserActionCallback callback) {
        super.onCreate(learningItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle=new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_ITEMS,learningItem);
                setFragment(R.id.fl_main, new LearnigItemDetailFragment(), "Learming Item 2",true, null, bundle);
            }
        });
    }
    @Override
    public void onEdit(final LearningItem learningItem, UserActionCallback callback) {
        super.onEdit(learningItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle=new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_ITEMS,learningItem);
                setFragment(R.id.fl_main, new LearnigItemDetailFragment(), "Learming Item 2",true, null, bundle);
            }
        });
    }


}
