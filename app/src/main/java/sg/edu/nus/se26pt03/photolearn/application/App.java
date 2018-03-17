package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;

import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;

/**
 * Created by MyatMin on 9/3/18.
 */
public class App extends Application {
    public static User currentUser = new Trainer();
    public  static AccessMode currentAccessMode = AccessMode.EDIT;
    public static AppMode currentAppMode = AppMode.PARTICIPENT;
}



