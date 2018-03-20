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
        dao.setId(title.id);
        dao.setTitle(title.title);
        dao.setLearningSessionId(title.sessionId);
        dao.setCreatedBy(title.createdBy);
        dao.setTimestamp(title.timestamp);

        return dao;
    }

    public static LearningTitle fromLearningTitle(LearningTitleDAO titleDAO) {
        LearningTitle title = new LearningTitle();
        title.id = titleDAO.getId();
        title.sessionId = titleDAO.getLearningSessionId();
        title.title = titleDAO.getTitle();
        title.createdBy = titleDAO.getCreatedBy();
        title.timestamp = titleDAO.getTimestamp();

        return title;
    }

    public static QuizTitleDAO toQuizTitleDao(QuizTitle title) {
        QuizTitleDAO dao = new QuizTitleDAO();
        dao.setId(title.id);
        dao.setTitle(title.title);
        dao.setLearningSessionId(title.sessionId);
        dao.setCreatedBy(title.createdBy);
        dao.setTimestamp(title.timestamp);

        return dao;
    }

    public static QuizTitle fromQuizTitle(QuizTitleDAO titleDAO) {
        QuizTitle title = new QuizTitle();
        title.id = titleDAO.getId();
        title.sessionId = titleDAO.getLearningSessionId();
        title.title = titleDAO.getTitle();
        title.createdBy = titleDAO.getCreatedBy();
        title.timestamp = titleDAO.getTimestamp();

        return title;
    }
}
