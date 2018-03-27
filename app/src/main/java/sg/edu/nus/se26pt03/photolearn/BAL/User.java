package sg.edu.nus.se26pt03.photolearn.BAL;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public abstract class User{
    private FirebaseUser firebaseUser;

    protected transient LearningSessionService learningSessionService = new LearningSessionService();

    public User(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public String getId() {
        return firebaseUser.getUid();
    }

    public String getDisplayName() {
            return firebaseUser.getDisplayName();
    }

    public Uri getPhotoUrl() {
        return firebaseUser.getPhotoUrl();
    }

    public Trainer toTrainer() {
        return new Trainer(firebaseUser);
    }

    public Participant toParticipant() {
        return new Participant(firebaseUser);
    }


    public abstract void getLearningSessions(ServiceCallback<List<LearningSession>> callback);

}
