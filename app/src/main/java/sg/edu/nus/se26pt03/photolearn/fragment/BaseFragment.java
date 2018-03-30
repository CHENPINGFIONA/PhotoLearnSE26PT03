package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.activity.BaseActivity;
import sg.edu.nus.se26pt03.photolearn.adapter.AppFragmentPagerAdapter;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.application.UserActionEmitter;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;

/**
 * Created by MyatMin on 10/3/18.
 */

public class BaseFragment extends Fragment implements UserActionListener {
    public static final String ARG_TITLE = "Title";
    public static final String ARG_STACKBACK = "StackBack";

    private  String mTitle;
    private  Boolean mStackBack = false;

    private UserActionEmitter userActionEmitter = new UserActionEmitter(this);

    @Override
    public List<UserActionListener> getRelatives() {
        List<UserActionListener> relatives = new ArrayList<UserActionListener>();
        if (getParentFragment() instanceof UserActionListener) relatives.add((UserActionListener) getParentFragment());
        if (getParentFragment() == null) {
            if (getActivity() instanceof UserActionListener)
                relatives.add((UserActionListener) getActivity());
        }
        for(Fragment fragment: getChildFragmentManager().getFragments()) {
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
    public void onResume() {
        super.onResume();
        SynchronizeLayout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mStackBack = getArguments().getBoolean(ARG_STACKBACK);
        }
        SynchronizeLayout();

    }

    public String getTitle() {
        return mTitle;
    }

    public Boolean getStackBack() {
        return mStackBack;
    }

    private void SynchronizeLayout() {
        if (mTitle != null) {
            ((BaseActivity) getActivity()).setTitle(mTitle, mStackBack);
        }
    }

    protected void setFragment(int containerViewId,BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }
    protected void setupTabLayout(int tabLayoutId, int viewPagerId) {
        final TabLayout tabLayout = getView().findViewById(tabLayoutId);
        final ViewPager viewPager = getView().findViewById(viewPagerId);
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
    protected void addFragment(int containerViewId, BaseFragment fragment, String title) {
        ViewPager viewPager = getView().findViewById(containerViewId);
        AppFragmentPagerAdapter appFragmentPagerAdapter = (AppFragmentPagerAdapter) viewPager.getAdapter();
        if (appFragmentPagerAdapter == null)
        {
            appFragmentPagerAdapter = new AppFragmentPagerAdapter(getChildFragmentManager());

        }
        appFragmentPagerAdapter.addFragment(fragment, title);
        viewPager.setAdapter(appFragmentPagerAdapter);

    }

    protected void refresh(String tag) {
        Fragment fragment = null;
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
        }

    protected void displayInfoMessage(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

    protected void displayErrorMessage(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

    protected void hideSoftInput (IBinder token) {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
