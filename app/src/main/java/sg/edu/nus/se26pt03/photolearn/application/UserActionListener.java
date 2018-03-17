package sg.edu.nus.se26pt03.photolearn.application;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by MyatMin on 13/3/18.
 */

public interface UserActionListener {
    enum Event{
        BEFORE,
        MODE_CHANGE,
        LOGIN,
        LOGOUT,
        LOAD,
        CREATE,
        EDIT,
        BACKSTACK
    }
    List<UserActionListener> getRelatives();

    boolean onBefore(Event event, UserActionListener source);
    boolean onModeChange(AppMode appMode, UserActionListener source);
    boolean onModeChange(AccessMode accessMode, UserActionListener source);
    boolean onLogIn(User user, UserActionListener source);
    boolean onLogOut(User user, UserActionListener source);
    boolean onLoad(Object object, UserActionListener source);
    boolean onCreate(Object object, UserActionListener source);
    boolean onEdit(Object object, UserActionListener source);
    boolean onBackstack(Object object, UserActionListener source);

    boolean onBefore(Event event);
    boolean onModeChange(AppMode appMode);
    boolean onModeChange(AccessMode accessMode);
    boolean onLogIn(User user);
    boolean onLogOut(User user);
    boolean onLoad(LearningSession learningSession);
    boolean onLoad(LearningTitle learningTitle);
    boolean onCreate(LearningSession learningSession);
    boolean onEdit(LearningSession learningSession);
    boolean onBackstack(Object object);

//    boolean dynamicRoute(Event event, boolean outbound, Object object);

}
