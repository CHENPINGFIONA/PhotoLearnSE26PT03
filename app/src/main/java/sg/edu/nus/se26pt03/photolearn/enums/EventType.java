package sg.edu.nus.se26pt03.photolearn.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by MyatMin on 27/3/18.
 */

public class EventType {
    public static final int BEFORE = 0;
    public static final int APPMODE_CHANGE = 1;
    public static final int ACCESSMODE_CHANGE = 2;
    public static final int LOGIN = 3;
    public static final int LOGOUT = 4;
    public static final int LOAD = 5;
    public static final int CREATE = 6;
    public static final int EDIT = 7;
    public static final int SUMMARY = 8;
    public static final int BACKSTACK = 9;



    public EventType(@Event int event) {
    }

    @IntDef({BEFORE, APPMODE_CHANGE, ACCESSMODE_CHANGE, LOGIN, LOGOUT, LOAD, CREATE, EDIT, SUMMARY, BACKSTACK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {}
}