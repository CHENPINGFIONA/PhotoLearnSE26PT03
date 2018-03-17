package sg.edu.nus.se26pt03.photolearn.fragment;


import android.content.Context;
import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.activity.BaseActivity;
import sg.edu.nus.se26pt03.photolearn.adapter.AppFragmentPagerAdapter;
import sg.edu.nus.se26pt03.photolearn.application.AccessMode;
import sg.edu.nus.se26pt03.photolearn.application.AppMode;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.application.UserActionRouter;

/**
 * Created by MyatMin on 10/3/18.
 */

public class BaseFragment extends Fragment implements UserActionListener {
    public static final String ARG_TITLE = "Title";
    public static final String ARG_STACKBACK = "StackBack";

    private  String mTitle;
    private  Boolean mStackBack = false;

    private UserActionRouter userActionRouter = new UserActionRouter(this);

    @Override
    public List<UserActionListener> getRelatives() {
        List<UserActionListener> relatives = new ArrayList<UserActionListener>();
        if (getParentFragment() instanceof UserActionListener) relatives.add((UserActionListener) getParentFragment());
        if (getParentFragment() == null) {
            if (getActivity() instanceof UserActionListener)
                relatives.add((UserActionListener) getActivity());
        }
        for(Fragment fragment: getChildFragmentManager().getFragments()) {
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

}
