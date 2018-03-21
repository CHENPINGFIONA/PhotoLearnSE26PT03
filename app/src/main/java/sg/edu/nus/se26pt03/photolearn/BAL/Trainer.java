package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Trainer extends User{
    private List<LearningSession> learningSessions = new ArrayList<LearningSession>();

    public boolean addLearningSession(LearningSession learningSession) {
        return learningSessions.add(learningSession);
    }

    public boolean addLearningSession(List<LearningSession> learningSessions) {
        for (LearningSession learningSession: learningSessions ) {
            if (!addLearningSession(learningSession))
                return false;
        }
        return  true;
    }

    public boolean removeLearningSession(LearningSession learningSession) {
        return learningSessions.remove(learningSession);
    }

    public List<LearningSession> getLearningSessions() {
        return (new ArrayList<LearningSession>(learningSessions));
    }

    public LearningSession getLearningSession(int index) {
        return learningSessions.get(index);
    }
}
