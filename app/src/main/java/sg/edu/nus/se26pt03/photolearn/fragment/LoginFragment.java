package sg.edu.nus.se26pt03.photolearn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PublicKey;
import java.util.Arrays;

import sg.edu.nus.se26pt03.photolearn.BAL.Participant;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LoginFragment extends BaseFragment {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLogIn(App.signIn(mAuth), null);
                }
            }, 1000);
        }else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setLogo(R.drawable.ic_photolearn_withtext)
                            .setTheme(R.style.AppThemeFirebaseAuth)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                onLogIn(App.signIn(FirebaseAuth.getInstance()),null);
            } else {

            }
        }
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Trainer trainer = new Trainer();
//                trainer.setId("facebook_user1");
//                onLogIn(trainer, null);
//            }
//        });
//        view.findViewById(R.id.btn_google).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Participant participant = new Participant();
//                participant.setId("google_user1");
//                onLogIn(participant, null);
//            }
//        });
//    }
}
