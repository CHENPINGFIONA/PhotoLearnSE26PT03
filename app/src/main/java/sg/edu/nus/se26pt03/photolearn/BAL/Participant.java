package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Participant extends User {
    public LearningSession getLearningSession(int sessionId) {
        return new LearningSession();
    }

//    public LearningSession getLearningSessions(String learningSessionId) {
//        return new LearningSessionService().getById(ler );
//    }
//
//    public void addLearningTitle(Title title){
//
//    }
//
//    public void StartQuiz(int QuizId){
//
//    }
}
