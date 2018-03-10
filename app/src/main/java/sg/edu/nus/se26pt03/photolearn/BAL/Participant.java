package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Participant extends User {


    public LearningSession getLearningSession(int sessionID) {
        return new LearningSession();
    }

    public List<LearningSession> getLearningSessions() {
        return (new ArrayList<LearningSession>());
    }

    public void addLearningTitle(Title title){

    }

    public void StartQuiz(int QuizId){

    }
}
