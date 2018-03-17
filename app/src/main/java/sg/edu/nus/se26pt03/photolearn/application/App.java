package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;

/**
 * Created by MyatMin on 9/3/18.
 */
public class App extends Application {
    public static LearningSession session;
    public static User currentUser;
    public static AccessMode currentAccessMode;
    public static AppMode currentAppMode;

    @Override
    public void onCreate() {
        super.onCreate();
        session = new LearningSession();
        currentUser = new Trainer();
        currentAccessMode = AccessMode.EDIT;
        currentAppMode = AppMode.PARTICIPENT;
    }
}



