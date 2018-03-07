package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class User {
    private int ID;
    private String loginID;
    private String loginSource;
    private Date lastLoginDate;
    private Date timestamp;

    public void createLearningSession(LearningSession session, int userID) {

    }

    public void updateLearningSession(LearningSession session, int userID) {

    }

    public void deleteLearningSession(int sessionID) {

    }

    public List<LearningSession> getLearningSessionsByTrainer() {
        return (new ArrayList<LearningSession>());
    }

    public List<LearningSession> getLearningSessionsByParticipant() {
        return (new ArrayList<LearningSession>());
    }

    public LearningSession getLearningSession(int sessionID) {
        return new LearningSession();
    }
}
