package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Trainer {
    private User user;

    public Trainer(User user) {
        this.user = user;
    }

    public void createLearningSession(LearningSession session, int userID) {

    }

    public void updateLearningSession(LearningSession session, int userID) {

    }

    public void deleteLearningSession(int sessionID) {

    }

    public List<LearningSession> getLearningSessions() {
        return (new ArrayList<LearningSession>());
    }
}
