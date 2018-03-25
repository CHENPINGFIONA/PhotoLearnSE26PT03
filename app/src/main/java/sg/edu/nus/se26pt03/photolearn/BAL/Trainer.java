package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 3/8/2018.
 */

public class Trainer extends User{
    @Override
    public void getLearningSessions(ServiceCallback<List<LearningSession>> callback) {
        learningSessionService.getAllByKeyValue("createdBy", this.getId(), callback);
    }
}
