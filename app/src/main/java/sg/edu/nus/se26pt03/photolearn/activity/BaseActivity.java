package sg.edu.nus.se26pt03.photolearn.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.application.UserActionRouter;
import sg.edu.nus.se26pt03.photolearn.fragment.BaseFragment;

/**
 * Created by MyatMin on 12/3/18.
 */

public class BaseActivity extends AppCompatActivity implements UserActionListener{

    private UserActionRouter userActionRouter = new UserActionRouter(this);

    @Override
    public final List<UserActionListener> getRelatives() {
        List<UserActionListener> relatives = new ArrayList<UserActionListener>();
        for(Fragment fragment: getSupportFragmentManager().getFragments()) {
            if (fragment instanceof UserActionListener) relatives.add((UserActionListener) fragment);
        }
        return  relatives;
    }

    @Override
    public final boolean onModeChange(AppMode appMode, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.MODE_CHANGE, false, appMode, source);
    }

    @Override
    public final boolean onModeChange(AccessMode accessMode, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.MODE_CHANGE, false, accessMode, source);
    }

    @Override
    public final boolean onLogIn(User user, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.LOGIN, false, user, source);
    }

    @Override
    public final boolean onLogOut(User user, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.LOGOUT, false, user, source);
    }

    @Override
    public final boolean onBefore(Event event, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.BEFORE, false, event, source);
    }

    @Override
    public final boolean onLoad(Object object, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.LOAD, false, object, source);
    }

    @Override
    public final boolean onCreate(Object object, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.CREATE, false, object, source);
    }

    @Override
    public final boolean onEdit(Object object, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.EDIT, false, object, source);
    }

    @Override
    public final boolean onBackstack(Object object, UserActionListener source) {
        return userActionRouter.dynamicRoute( Event.BACKSTACK, false, object, source);
    }
    @Override
    public boolean onBefore(Event event) {
        return userActionRouter.dynamicRoute(Event.BEFORE, true, event, null);
    }

    @Override
    public boolean onModeChange(AppMode appMode) {
        return userActionRouter.dynamicRoute(Event.MODE_CHANGE, true, appMode, null);
    }

    @Override
    public boolean onModeChange(AccessMode accessMode) {
        return userActionRouter.dynamicRoute(Event.MODE_CHANGE, true, accessMode, null);
    }

    @Override
    public  boolean onLogIn(User user) {
        return userActionRouter.dynamicRoute(Event.LOGIN, true, user, null);
    }

    @Override
    public boolean onLogOut(User user) {
        return userActionRouter.dynamicRoute(Event.LOGOUT, true, user, null);
    }

    @Override
    public boolean onLoad(LearningSession learningSession) {
        return userActionRouter.dynamicRoute(Event.LOAD, true, learningSession, null);
    }

    @Override
    public boolean onLoad(LearningTitle learningTitle) {
        return userActionRouter.dynamicRoute(Event.LOAD, true, learningTitle, null);
    }

    @Override
    public boolean onCreate(LearningSession learningSession) {
        return userActionRouter.dynamicRoute(Event.CREATE, true, learningSession, null);
    }

    @Override
    public boolean onEdit(LearningSession learningSession) {
        return userActionRouter.dynamicRoute(Event.EDIT, true, learningSession, null);
    }

    @Override
    public boolean onBackstack(Object object) {
        return userActionRouter.dynamicRoute(Event.BACKSTACK, true, object, null);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            if (onBackstack(null)) getSupportFragmentManager().popBackStack();

        } else {
            //super.onBackPressed();
        }
    }

    protected void setFragment(int containerViewId, BaseFragment fragment, String title, Boolean stackback, String tag, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(fragment.ARG_TITLE, title);
        bundle.putBoolean(fragment.ARG_STACKBACK, stackback);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        if (stackback) {
            if (tag != null) {
                fragmentTransaction.addToBackStack(tag);
            }
            else {
                fragmentTransaction.addToBackStack(fragment.getClass().getName());
            }
        }
        fragmentTransaction.commit();
    }

    public void setTitle(String title, boolean backstack) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(backstack);
            actionBar.setDisplayShowHomeEnabled(backstack);
            actionBar.setTitle(title);
        }
    }

}
