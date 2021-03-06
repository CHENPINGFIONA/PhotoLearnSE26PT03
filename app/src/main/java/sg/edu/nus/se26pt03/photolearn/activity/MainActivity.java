package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;

import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.fragment.LoginFragment;
import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;

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
                App.setCurrentUser(user);
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
