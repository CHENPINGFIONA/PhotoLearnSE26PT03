package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;

import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.fragment.LoginFragment;

public class MainActivity extends BaseActivity{
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
    public boolean onLogIn(User user) {
        App.currentUser = user;
        Intent intent = new Intent(this, LearningActivity.class);
        startActivity(intent);
        return super.onLogIn(user);
    }
}
