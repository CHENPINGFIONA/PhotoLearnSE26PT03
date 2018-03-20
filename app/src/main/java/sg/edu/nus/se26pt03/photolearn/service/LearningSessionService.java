package sg.edu.nus.se26pt03.photolearn.service;

import android.animation.TypeConverter;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;

/**
 * Created by Drake on 21/3/18.
 */

public class LearningSessionService {

    public LearningSession convert(LearningSessionDAO value) {
        LearningSession learningSession = new LearningSession();
        learningSession.setId(value.getId());
        learningSession.setCourseDate(value.getCourseDate());
        learningSession.setCourseCode(value.getCourseCode());
        learningSession.setCourseName(value.getCourseName());
        learningSession.setModuleNumber(value.getModuleNumber());
        learningSession.setModuleName(value.getModuleName());
        return learningSession;
    }

}
