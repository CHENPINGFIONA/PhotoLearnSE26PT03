package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;

/**
 * Created by MyatMin on 9/3/18.
 */
public class App extends Application {
    public static LearningSession session;

    @Override
    public void onCreate() {
        super.onCreate();
        session = new LearningSession();
    }
}



