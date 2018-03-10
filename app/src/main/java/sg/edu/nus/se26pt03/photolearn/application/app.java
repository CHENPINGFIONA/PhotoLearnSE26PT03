package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;

/**
 * Created by MyatMin on 9/3/18.
 */
public class App extends Application {
    public enum UserMode {
        TRAINER,
        PARTICIPENT
    }
    public enum AccessMode {
        EDIT,
        VIEW
    }
    public enum Action {
        CREATED,
        SELECTED,
        EDITED,
        DELETED

    }


}


