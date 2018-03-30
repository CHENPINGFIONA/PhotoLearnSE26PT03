package sg.edu.nus.se26pt03.photolearn.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.application.UserActionEmitter;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;
import sg.edu.nus.se26pt03.photolearn.fragment.BaseFragment;

/**
 * Created by MyatMin on 12/3/18.
 */

public class BaseActivity extends AppCompatActivity implements UserActionListener{

    private UserActionEmitter userActionEmitter = new UserActionEmitter(this);

    @Override
    public final List<UserActionListener> getRelatives() {
        List<UserActionListener> relatives = new ArrayList<UserActionListener>();
        for(Fragment fragment: getSupportFragmentManager().getFragments()) {
            if (fragment instanceof UserActionListener && fragment.isVisible()) relatives.add((UserActionListener) fragment);
        }
        return  relatives;
    }

    @Override
    public final void onAppModeChange(@AppMode.Mode int mode, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.APPMODE_CHANGE, false, mode, callback, source);
    }

    @Override
    public final void onAccessModeChange(@AccessMode.Mode int mode, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.ACCESSMODE_CHANGE, false, mode,callback,  source);
    }

    @Override
    public final void onLogIn(User user, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.LOGIN, false, user,callback,  source);
    }

    @Override
    public final void onLogOut(User user, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.LOGOUT, false, user, callback, source);
    }

    @Override
    public final void onBefore(@EventType.Event int event, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.BEFORE, false, event,  callback, source);
    }

    @Override
    public final void onLoad(Object object, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.LOAD, false, object, callback, source);
    }

    @Override
    public final void onCreate(Object object, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.CREATE, false, object, callback, source);
    }

    @Override
    public final void onEdit(Object object, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.EDIT, false, object, callback, source);
    }

    @Override
    public final void onSummary(Object object, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.SUMMARY, false, object, callback, source);
    }

    @Override
    public final void onBackstack(Object object, UserActionCallback callback, UserActionListener source) {
        userActionEmitter.dynamicEmit( EventType.BACKSTACK, false, object, callback, source);
    }

    @Override
    public void onBefore(@EventType.Event int event, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.BEFORE, true, event, callback, null  );
    }

    @Override
    public void onAppModeChange(@AppMode.Mode int mode, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.APPMODE_CHANGE, true, mode, callback, null);
    }

    @Override
    public void onAccessModeChange(@AccessMode.Mode int mode, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.ACCESSMODE_CHANGE, true, mode, callback, null);
    }

    @Override
    public  void onLogIn(User user, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOGIN, true, user, callback, null);
    }

    @Override
    public void onLogOut(User user, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOGOUT, true, user, callback, null);
    }

    @Override
    public void onLoad(LearningSession learningSession, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, learningSession, callback, null);
    }

    @Override
    public void onLoad(LearningTitle learningTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, learningTitle, callback, null);
    }

    @Override
    public void onLoad(LearningItem learningItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, learningItem, callback, null);
    }

    @Override
    public void onLoad(QuizTitle quizTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, quizTitle, callback, null);
    }

    @Override
    public void onLoad(QuizItem quizItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, quizItem, callback, null);
    }

    @Override
    public void onLoad(QuizAnswer quizAnswer, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.LOAD, true, quizAnswer, callback, null);
    }

    @Override
    public void onCreate(LearningSession learningSession, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, learningSession, callback, null);
    }

    @Override
    public void onCreate(LearningTitle learningTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, learningTitle, callback, null);
    }

    @Override
    public void onCreate(LearningItem learningItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, learningItem, callback, null);
    }

    @Override
    public void onCreate(QuizTitle quizTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, quizTitle, callback, null);
    }

    @Override
    public void onCreate(QuizItem quizItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, quizItem, callback, null);
    }

    @Override
    public void onCreate(QuizAnswer quizAnswer, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.CREATE, true, quizAnswer, callback, null);
    }

    @Override
    public void onEdit(LearningSession learningSession, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, learningSession, callback, null);
    }

    @Override
    public void onEdit(LearningTitle learningTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, learningTitle, callback, null);
    }

    @Override
    public void onEdit(LearningItem learningItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, learningItem, callback, null);
    }

    @Override
    public void onEdit(QuizTitle quizTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, quizTitle, callback, null);
    }

    @Override
    public void onEdit(QuizItem quizItem, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, quizItem, callback, null);
    }

    @Override
    public void onEdit(QuizAnswer quizAnswer, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.EDIT, true, quizAnswer, callback, null);
    }

    @Override
    public void onSummary(QuizTitle quizTitle, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.SUMMARY, true, quizTitle, callback, null);
    }

    @Override
    public void onBackstack(Object object, UserActionCallback callback) {
        userActionEmitter.dynamicEmit(EventType.BACKSTACK, true, object, callback, null);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            onBackstack(null, new UserActionCallback() {
                @Override
                public void onPass() {
                    getSupportFragmentManager().popBackStack();
                }
            });
        }
    }

    protected void setFragment(int containerViewId, BaseFragment fragment, String title, Boolean stackback, String tag, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (fragment.getArguments() != null) {
            bundle.putAll(fragment.getArguments());
        }
        bundle.putString(fragment.ARG_TITLE, title);
        bundle.putBoolean(fragment.ARG_STACKBACK, stackback);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, (tag == null ? fragment.getClass().getName(): tag));
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

    protected void displayInfoMessage(String message) {
        Snackbar.make(this.findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

    protected void displayErrorMessage(String message) {
        Snackbar.make(this.findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

}
