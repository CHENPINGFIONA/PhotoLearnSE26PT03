package sg.edu.nus.se26pt03.photolearn.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import com.google.firebase.auth.FirebaseAuth;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Participant;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by part time team 3  on 9/3/18.
 */
public class App extends Application {
//    public static LearningSession session;
    private static User currentUser;
    private static @AccessMode.Mode int currentAccessMode;
    private static @AppMode.Mode int currentAppMode;
    private static Context context;
    private static SharedPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        loadAppPreferences();
    }

    public static User signIn(FirebaseAuth firebaseAuth) {
        User user;
        if (App.getCurrentAppMode() == AppMode.TRAINER) {
            user = new Trainer(firebaseAuth.getInstance().getCurrentUser());
        }
        else {
            user = new Participant(firebaseAuth.getInstance().getCurrentUser());
        }
        return user;
    }

    public static void signOut(FirebaseAuth firebaseAuth) {
        setCurrentUser(null);
        firebaseAuth.signOut();
    }

    private void loadAppPreferences() {
        appPreferences = getSharedPreferences(ConstHelper.REF_APP_PREFERENCES, Context.MODE_PRIVATE);
        setCurrentAccessMode(appPreferences.getInt(ConstHelper.REF_APP_PREFERENCES_ACCESSMODE, AccessMode.VIEW));
        setCurrentAppMode(appPreferences.getInt(ConstHelper.REF_APP_PREFERENCES_APPMODE, AppMode.TRAINER));
    }
    public static  void changeAppMode(@AppMode.Mode int mode) {
        if (mode == AppMode.TRAINER) {
            setCurrentUser(new Trainer(FirebaseAuth.getInstance().getCurrentUser()));
        }
        else {
            setCurrentUser(new Participant(FirebaseAuth.getInstance().getCurrentUser()));
        }
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

    public static @AccessMode.Mode int getCurrentAccessMode() {
        return currentAccessMode;
    }

    public static void setCurrentAccessMode(@AccessMode.Mode int mode) {
        App.currentAccessMode = mode;
        SharedPreferences.Editor editor =  appPreferences.edit();
        editor.putInt(ConstHelper.REF_APP_PREFERENCES_ACCESSMODE, mode);
        editor.commit();
    }

    public static void setCurrentAppMode(@AppMode.Mode int mode) {
        App.currentAppMode = mode;
        SharedPreferences.Editor editor =  appPreferences.edit();
        editor.putInt(ConstHelper.REF_APP_PREFERENCES_APPMODE, mode);
        editor.commit();
    }

    public static @AppMode.Mode int getCurrentAppMode() {
        return currentAppMode;
    }

    private static void refreshCurrentAppMode() {
        if (currentUser instanceof Trainer){
            setCurrentAppMode(AppMode.TRAINER);
        }
        else {
            setCurrentAppMode(AppMode.PARTICIPENT);
        }
    }

}



