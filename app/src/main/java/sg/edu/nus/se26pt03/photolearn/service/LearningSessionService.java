package sg.edu.nus.se26pt03.photolearn.service;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningSessionRepo;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by MyatMin on 21/3/18.
 */

public class LearningSessionService extends BaseService<LearningSession, LearningSessionDAO> {

    private LearningSessionRepo learningSessionRepo = new LearningSessionRepo();

    public LearningSessionService() {
        setBaseRepo(learningSessionRepo);
        setDAOConversion(new DAOConversion<LearningSession, LearningSessionDAO>() {
            @Override
            public LearningSession convertFromDAO(LearningSessionDAO value) {
                LearningSession learningSession = new LearningSession();
                learningSession.setId(value.getId());
                learningSession.setCourseDate(new Date(value.getCourseDate()));
                learningSession.setCourseCode(value.getCourseCode());
                learningSession.setCourseName(value.getCourseName());
                learningSession.setModuleNumber(value.getModuleNumber());
                learningSession.setModuleName(value.getModuleName());
                learningSession.setCreatedBy(value.getCreatedBy());
                learningSession.setTimestamp(new Date(value.getTimestamp()));
                return learningSession;
            }

            @Override
            public LearningSessionDAO convertToDAO(LearningSession value) {
                LearningSessionDAO learningSessionDAO = new LearningSessionDAO();
                learningSessionDAO.setId(value.getId());
                learningSessionDAO.setCourseDate(value.getCourseDate().getTime());
                learningSessionDAO.setCourseCode(value.getCourseCode());
                learningSessionDAO.setCourseName(value.getCourseName());
                learningSessionDAO.setModuleNumber(value.getModuleNumber());
                learningSessionDAO.setModuleName(value.getModuleName());
                learningSessionDAO.setCreatedBy(value.getCreatedBy());
                learningSessionDAO.setCreatedBy(value.getCreatedBy());
                learningSessionDAO.setLearningSessionId(value.getLearningSessionId());
                return learningSessionDAO;
            }
        });
    }
}
