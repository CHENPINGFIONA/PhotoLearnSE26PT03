package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.Snackbar;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Participant;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;

/**
 * Created by MyatMin on 9/3/18.
 */
public class App extends Application {
    public static LearningSession session;
    public static User currentUser;
    public static AccessMode currentAccessMode;
    public static AppMode currentAppMode;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        session = new LearningSession();
        currentUser = new Trainer();
        currentAccessMode = AccessMode.EDIT;
        currentAppMode = AppMode.TRAINER;
    }
    public static Context getContext(){
        return context;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
        refreshCurrentAppMode();
    }

    public static AccessMode getCurrentAccessMode() {
        return currentAccessMode;
    }

    public static void setCurrentAccessMode(AccessMode currentAccessMode) {
        App.currentAccessMode = currentAccessMode;
    }

    public static AppMode getCurrentAppMode() {
        return currentAppMode;
    }

    private static void refreshCurrentAppMode() {
        if (currentUser instanceof Trainer){
            currentAppMode = AppMode.TRAINER;
        }
        else {
            currentAppMode = AppMode.PARTICIPENT;
        }
    }
}



