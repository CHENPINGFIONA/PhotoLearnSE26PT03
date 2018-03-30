package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.QuizTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by MyatMin on 21/3/18.
 */

public class QuizTitleService extends BaseService<QuizTitle, QuizTitleDAO> {
    private QuizTitleRepo quizTitleRepo = new QuizTitleRepo();
    private  LearningSession learningSession;
    public QuizTitleService(LearningSession learningSession) {
        setBaseRepo(quizTitleRepo);
        this.learningSession=learningSession;
        setDAOConversion(new DAOConversion<QuizTitle, QuizTitleDAO>() {
            @Override
            public QuizTitle convertFromDAO(QuizTitleDAO value) {
                QuizTitle quizTitle = new QuizTitle();
                quizTitle.setId(value.getId());
                quizTitle.setLearningSession(learningSession);
                quizTitle.setTitle(value.getTitle());
                quizTitle.setCreatedBy(value.getCreatedBy());
                quizTitle.setTimestamp(new Date(value.getTimestamp()));
                return quizTitle;
            }

            @Override
            public QuizTitleDAO convertToDAO(QuizTitle value) {
                QuizTitleDAO quizTitleDAO = new QuizTitleDAO();
                quizTitleDAO.setId(value.getId());
                quizTitleDAO.setLearningSessionId(value.getLearningSession().getId());
                quizTitleDAO.setTitle(value.getTitle());
                quizTitleDAO.setCreatedBy(value.getCreatedBy());
                return quizTitleDAO;
            }
        });
    }

    public void getAllByLearningSessionId(String id, final String text, final ServiceCallback<List<QuizTitle>> callback) {
        quizTitleRepo.getAllByKeyValue("learningSessionId", id, new RepoCallback<List<QuizTitleDAO>>() {
            @Override
            public void onComplete(List<QuizTitleDAO> data) {
                List<QuizTitleDAO> daos = new ArrayList<>();
                for (QuizTitleDAO titleDao : data) {
                    if (titleDao.getTitle().contains(text)) {
                        daos.add(titleDao);
                    }
                }
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }
}
