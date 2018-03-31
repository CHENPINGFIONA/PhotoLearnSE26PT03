package sg.edu.nus.se26pt03.photolearn.BAL;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by part time team 3 on 3/8/2018.
 */

public class Participant extends User {
    
    public Participant(FirebaseUser firebaseUser) {
        super(firebaseUser);
    }

    @Override
    public void getLearningSessions(ServiceCallback<List<LearningSession>> callback) {
        learningSessionService.getAllByKeyValue("participants."  + this.getId(), true, callback);
    }

    public void accessLearnigSession(String learningSessionId, ServiceCallback<Boolean> callback) {
        learningSessionService.getAllByKeyValue("learningSessionId", learningSessionId, new ServiceCallback<List<LearningSession>>() {
            @Override
            public void onComplete(List<LearningSession> data) {
                if (data != null && data.size() == 0) {callback.onError(-1, "No learning session found", "");}
                for (LearningSession learningSession : data) {
                    learningSessionService.setValueByKey(learningSession.getId() + ".participants." + getId(), true, callback);
                }
            }

            @Override
            public void onError(int code, String message, String details) {
                callback.onError(code, message, details);
            }
        });
    }
}
