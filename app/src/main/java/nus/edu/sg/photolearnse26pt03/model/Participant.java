package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Participant {
    private User user;

    public Participant(User user) {
        this.user = user;
    }

    public LearningSession getLearningSession(int sessionID) {
        return new LearningSession();
    }

    public List<LearningSession> getLearningSessions() {
        return (new ArrayList<LearningSession>());
    }
}
