package sg.edu.nus.se26pt03.photolearn.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionListFragment;

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
                //onModeChange(App.currentAppMode);
                onLoad(new LearningTitle());
            }
        });
    }

    @Override
    public boolean onLoad(LearningSession learningSession) {
        if (super.onLoad(learningSession)) setFragment(R.id.fl_main, new LearningSessionFragment(), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
        return true;
    }

    @Override
    public boolean onEdit(LearningSession learningSession) {
        if (super.onEdit(learningSession)) setFragment(R.id.fl_main, new LearningSessionDetailFragment(), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
        return true;
    }

    @Override
    public boolean onCreate(LearningSession learningSession) {
        if (super.onCreate(learningSession)) setFragment(R.id.fl_main, new LearningSessionDetailFragment(), "New Learning Session",true, null, null);
        return true;
    }

    @Override
    public boolean onModeChange(AppMode appMode) {
        if (super.onModeChange(appMode)) {
            finish();
            startActivity(getIntent());
        }
        return true;
    }

    @Override
    public boolean onLoad(LearningTitle learningTitle) {
        if (super.onLoad(learningTitle)) setFragment(R.id.fl_main, new LearningItemListFragment(), "Someing Learning Title", true, null, null);
    return true;
    }
}
