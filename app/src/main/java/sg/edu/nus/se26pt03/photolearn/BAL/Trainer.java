package sg.edu.nus.se26pt03.photolearn.BAL;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Trainer extends User{

    public Trainer(FirebaseUser firebaseUser) {
        super(firebaseUser);
    }

    @Override
    public void getLearningSessions(ServiceCallback<List<LearningSession>> callback) {
        learningSessionService.getAllByKeyValue("createdBy", this.getId(), callback);
    }

    public void createLearningSession(LearningSession learningSession, ServiceCallback<LearningSession> callback) {
        learningSessionService.save(learningSession, callback);
    }

    public void updateLearningSession(LearningSession learningSession, ServiceCallback<Boolean> callback) {
        learningSessionService.update(learningSession, callback);
    }

    public void deleteLearningSession(LearningSession learningSession, ServiceCallback<Boolean> callback) {
        learningSessionService.delete(learningSession, callback);
    }

}
