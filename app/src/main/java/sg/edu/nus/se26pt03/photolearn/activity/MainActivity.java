package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.security.PrivateKey;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.AccessMode;
import sg.edu.nus.se26pt03.photolearn.application.Action;

import sg.edu.nus.se26pt03.photolearn.application.UserMode;
import sg.edu.nus.se26pt03.photolearn.fragment.HomeFragment;
import sg.edu.nus.se26pt03.photolearn.fragment.LoginFragment;
import sg.edu.nus.se26pt03.photolearn.application.AppFragment;

public class MainActivity extends AppCompatActivity implements AppFragment.AppEventListener, AppFragment.LearningEventListener {
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fl_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            setFragment(new LoginFragment());
        }

    }

    @Override
    public void onModeChanged(UserMode userMode, AccessMode accessMode) {
        Log.d("Trigger", "onModeChanged");
    }

    @Override
    public void onLoggedIn() {
        homeFragment= HomeFragment.newInstance("Welcome to PhotoLearn");
        setFragment(homeFragment);
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLearningSessionReaction(Action action) {
        switch (action) {
            case SELECTED:
                homeFragment.setTitle("Learning Session 1", true);
                break;

        }
    }

    @Override
    public void onLearningTitleReaction(Action action) {

    }

    @Override
    public void onLearningItemReaction(Action action) {

    }

    @Override
    public void onQuizTitleReaction(Action action) {

    }

    @Override
    public void onQuizItemReaction(Action action) {

    }

    @Override
    public void onQuizAnswerReaction(Action action) {

    }

    //
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
}
