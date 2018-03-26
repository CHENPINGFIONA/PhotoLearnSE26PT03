package sg.edu.nus.se26pt03.photolearn.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
        setupControls();
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
                setFragment(R.id.fl_main, LearningSessionFragment.newInstance(learningSession), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
            }
        });
    }

    @Override
    public void onEdit(final LearningSession learningSession, UserActionCallback callback) {
        super.onCreate(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main,  LearningSessionDetailFragment.newInstance(learningSession), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(),true, null, null);
            }
        });
    }

    @Override
    public void onCreate(final LearningSession learningSession, UserActionCallback callback) {
        super.onEdit(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, LearningSessionDetailFragment.newInstance(learningSession), "New Learning Session",true, null, null);
            }
        });
    }

    /*
    * Learning Items Declarations Start Here
    *
    * */
    @Override
    public void onLoad(final LearningTitle learningTitle, UserActionCallback callback) {
        super.onLoad(learningTitle, new UserActionCallback(){
            @Override

            public void onPass() {
                Bundle bundle=new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_TITLES,learningTitle);
                setFragment(R.id.fl_main, new LearningItemListFragment(),"Learning Title 1", true, "TitleFragment", bundle);
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

    private void setupControls() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity.super.onBackPressed();
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.dl_learning);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.drawer_open,R.string.drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                    drawerLayout.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawerLayout.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });

        NavigationView navigationView = findViewById(R.id.nav_learning);
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;
            }
        });

        setFragment(R.id.fl_main, new LearningSessionListFragment(),"Welcome " + (App.getCurrentAppMode() == AppMode.TRAINER? "Trainer" : "Participant") + "!" ,false,null, null);
        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
//                if (App.getCurrentAppMode() == AppMode.TRAINER) App.getCurrentAppMode() == AppMode.PARTICIPENT;
//                else if (App.getCurrentAppMode() == AppMode.PARTICIPENT) App.getCurrentAppMode() = AppMode.TRAINER;
                //onModeChange(App.currentAppMode, null);
//                LearningTitle learningTitle= new LearningTitle();
//                learningTitle.setId("-L88Kii8Oc5tSrTBxNaW");
//                onLoad(learningTitle, null);
                //onLoad(new LearningSession(), null);
            }
        });


    }
    /*
    public void onClick(View v) {

        if (v.getId() == R.id.btn_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    //.delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                }
            });
        }
    }
    */



}
