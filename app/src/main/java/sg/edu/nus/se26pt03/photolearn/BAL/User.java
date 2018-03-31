package sg.edu.nus.se26pt03.photolearn.BAL;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by part time team 3 on 7/3/2018.
 */

public abstract class User{
    private String id;
    private String displayName;
    private Uri photoUrl;

    protected transient LearningSessionService learningSessionService = new LearningSessionService();

    public User(String id) {
        this.id = id;
    }

    public User(FirebaseUser firebaseUser) {
        setId(firebaseUser.getUid());
        setDisplayName(firebaseUser.getDisplayName());
        setPhotoUrl(firebaseUser.getPhotoUrl());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
            return displayName;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public abstract void getLearningSessions(ServiceCallback<List<LearningSession>> callback);

}
