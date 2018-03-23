package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;

import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.fragment.LoginFragment;
import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

public class MainActivity extends BaseActivity{
    private LearningSessionService learningSessionService = new LearningSessionService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fl_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            setFragment(R.id.fl_main, new LoginFragment(),null,false,null,null);
        }

    }

    @Override
    public void onLogIn(final User user, final UserActionCallback callback) {
        final Context context = this;
        super.onLogIn(user, new UserActionCallback() {
            @Override
            public void onPass() {
                App.currentUser = user;
                App.currentAppMode = AppMode.TRAINER;
                Intent intent = new Intent(context, LearningActivity.class);
                startActivity(intent);
                callback.onPass();
            }
            @Override
            public void onReject() {
                callback.onReject();
            }
        });
    }
}
