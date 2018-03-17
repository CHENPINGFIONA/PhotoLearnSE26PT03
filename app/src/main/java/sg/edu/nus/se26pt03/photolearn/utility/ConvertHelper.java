package sg.edu.nus.se26pt03.photolearn.utility;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;

/**
 * Created by chen_ on 15/3/2018.
 */

public class ConvertHelper {
    public static LearningTitleDAO toLearningTitleDao(LearningTitle title) {
        LearningTitleDAO dao = new LearningTitleDAO();
        dao.setTitle(title.title);
        dao.setLearningSessionId(title.sessionId);
        dao.setCreatedBy(title.createdBy);
        dao.setTimestamp(title.timestamp);

        return dao;
    }

    public static LearningTitle fromLearningTitle(LearningTitleDAO titleDAO) {
        return new LearningTitle(titleDAO.getLearningSessionId(), titleDAO.getTitle(), titleDAO.getCreatedBy());
    }

    public static QuizTitleDAO toQuizTitleDao(QuizTitle title) {
        QuizTitleDAO dao = new QuizTitleDAO();
        dao.setTitle(title.title);
        dao.setLearningSessionId(title.sessionId);
        dao.setCreatedBy(title.createdBy);
        dao.setTimestamp(title.timestamp);

        return dao;
    }

    public static QuizTitle fromQuizTitle(QuizTitleDAO titleDAO) {
        return new QuizTitle(titleDAO.getLearningSessionId(), titleDAO.getTitle(), titleDAO.getCreatedBy());
    }
}
