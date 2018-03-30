package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeechService;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.fragment.LearnigItemDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningItemListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LearningSessionListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizItemDetailFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizItemListFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.QuizSubmissionSummaryFragment;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;
import sg.edu.nus.se26pt03.photolearn.utility.TTSHelper;

public class LearningActivity extends BaseActivity {
    public static TTSHelper ttsHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        setupControls();
        LearningActivity.ttsHelper=new TTSHelper(getApplicationContext());
        displayInfoMessage("You are signing in as " + App.getCurrentUser().getDisplayName());
    }

    @Override
    public void onLogOut(User user, UserActionCallback callback) {
        super.onLogOut(user, new UserActionCallback() {
            @Override
            public void onPass() {
                App.signOut(FirebaseAuth.getInstance());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                if (callback != null) callback.onPass();
            }
        });
    }

    @Override
    public void onAppModeChange(@AppMode.Mode int mode, UserActionCallback callback) {
        super.onAppModeChange(mode, new UserActionCallback() {
            @Override
            public void onPass() {
                App.changeAppMode(mode);
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onAccessModeChange(int mode, UserActionCallback callback) {
        App.setCurrentAccessMode(mode);
        @AccessMode.Mode int orginalAccessMode = App.getCurrentAccessMode();
        super.onAccessModeChange(mode, new UserActionCallback() {
            @Override
            public void onReject() {
                App.setCurrentAccessMode(orginalAccessMode);
            }
        });
    }

    @Override
    public void onLoad(final LearningSession learningSession, UserActionCallback callback) {
        super.onLoad(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, LearningSessionFragment.newInstance(learningSession), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(), true, null, null);
            }
        });
    }

    @Override
    public void onEdit(final LearningSession learningSession, UserActionCallback callback) {
        super.onCreate(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, LearningSessionDetailFragment.newInstance(learningSession), learningSession.getModuleNumber() + ". " + learningSession.getModuleName(), true, null, null);
            }
        });
    }

    @Override
    public void onCreate(final LearningSession learningSession, UserActionCallback callback) {
        super.onEdit(learningSession, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, LearningSessionDetailFragment.newInstance(learningSession), "New Learning Session", true, null, null);
            }
        });
    }

    /*
    * Learning Items Declarations Start Here
    *
    * */
    @Override
    public void onLoad(final LearningTitle learningTitle, UserActionCallback callback) {
        super.onLoad(learningTitle, new UserActionCallback() {
            @Override

            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_TITLES, learningTitle);
                setFragment(R.id.fl_main, new LearningItemListFragment(), learningTitle.getTitle(), true, "TitleFragment", bundle);
            }
        });
    }

    @Override
    public void onCreate(final LearningItem learningItem, UserActionCallback callback) {
        super.onCreate(learningItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_ITEMS, learningItem);
                setFragment(R.id.fl_main, new LearnigItemDetailFragment(), "Add Item", true, null, bundle);
            }
        });
    }

    @Override
    public void onEdit(final LearningItem learningItem, UserActionCallback callback) {
        super.onEdit(learningItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_LEARNING_ITEMS, learningItem);
                setFragment(R.id.fl_main, new LearnigItemDetailFragment(), "Edit Item", true, null, bundle);
            }
        });
    }

    @Override
    public void onSummary(QuizTitle quizTitle, UserActionCallback callback) {
        super.onSummary(quizTitle, new UserActionCallback() {
            @Override
            public void onPass() {
                setFragment(R.id.fl_main, QuizSubmissionSummaryFragment.newInstance(quizTitle), "Summary - " + quizTitle.getTitle(), true, null, null);
            }
        });
    }

    /*
    * Quiz Item Starts Here
    *
    * */

    @Override
    public void onLoad(final QuizTitle quizTitle, UserActionCallback callback) {
        super.onLoad(quizTitle, new UserActionCallback() {
            @Override

            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_QUIZ_TITLES, quizTitle);
                setFragment(R.id.fl_main, new QuizItemListFragment(), quizTitle.getTitle(), true, "QuizFragment"+quizTitle.getId(), bundle);
            }
        });
    }

    @Override
    public void onCreate(final QuizItem quizItem, UserActionCallback callback) {
        super.onCreate(quizItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_QUIZ_ITEMS, quizItem);
                setFragment(R.id.fl_main, new QuizItemDetailFragment(), quizItem.getTitle().getTitle() + "/ Add New Item", true, "", bundle);
            }
        });
    }

    @Override
    public void onEdit(final QuizItem quizItem, UserActionCallback callback) {
        super.onEdit(quizItem, new UserActionCallback() {
            @Override
            public void onPass() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstHelper.REF_QUIZ_ITEMS, quizItem);
                setFragment(R.id.fl_main, new QuizItemDetailFragment(), quizItem.getTitle().getTitle() + "/ Change Item", true, null, bundle);
            }
        });
    }


    private void setupControls() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity.super.onBackPressed();
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.dl_learning);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.drawer_open,R.string.drawer_close);

        setFragment(R.id.fl_main, new LearningSessionListFragment(), "Welcome " + (App.getCurrentAppMode() == AppMode.TRAINER ? "Trainer" : "Participant") + "!", false, null, null);
        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }

            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        NavigationView navigationView = findViewById(R.id.nav_learning);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_displayname)).setText(App.getCurrentUser().getDisplayName());
        ImageView img_userprofilephoto = navigationView.getHeaderView(0).findViewById(R.id.img_user_profilephoto);
        AsyncLoadImageHelper loader = new AsyncLoadImageHelper(img_userprofilephoto, navigationView.getContext(), navigationView.getHeaderView(0).findViewById(R.id.pb_user_profilephoto));
        loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, App.getCurrentUser().getPhotoUrl().toString());
        MenuItem item_editmode = navigationView.getMenu().findItem(R.id.item_editmode);
        SwitchCompat item_editmodeswitch = item_editmode.getActionView().findViewById(R.id.item_editmode_switch);
        if (App.getCurrentAppMode() == AppMode.TRAINER) {
            navigationView.getMenu().findItem(R.id.item_trainermode).setChecked(true);
            item_editmode.setEnabled(false);
            item_editmodeswitch.setEnabled(false);
        } else {
            navigationView.getMenu().findItem(R.id.item_participantmode).setChecked(true);
        }
        item_editmodeswitch.setChecked((App.getCurrentAccessMode() == AccessMode.EDIT ? true : false));
        item_editmodeswitch.setOnClickListener(v -> {
            if (item_editmodeswitch.isChecked()) onAccessModeChange(AccessMode.EDIT, null);
            else onAccessModeChange(AccessMode.VIEW, null);
            ((DrawerLayout) findViewById(R.id.dl_learning)).closeDrawers();
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.item_participantmode:
//                        item_editmode.setEnabled(true);
//                        item_editmode_switch.setEnabled(true);
                        onAppModeChange(AppMode.PARTICIPENT, null);
                        break;
                    case R.id.item_trainermode:
//                        item_editmode.setEnabled(false);
//                        item_editmode_switch.setEnabled(false);
                        onAppModeChange(AppMode.TRAINER, null);
                        break;
                    case R.id.item_signout:
                        onLogOut(App.getCurrentUser(), null);
                    case R.id.item_editmode:
                        break;
                }
                ((DrawerLayout) findViewById(R.id.dl_learning)).closeDrawers();
                return true;
            }
        });
        return super.onPrepareOptionsMenu(menu);
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
