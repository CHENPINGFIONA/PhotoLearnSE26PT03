package sg.edu.nus.se26pt03.photolearn.service;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerDAO;
import sg.edu.nus.se26pt03.photolearn.database.QuizAnswerRepo;

/**
 * Created by Administrator on 2018/3/24.
 */

public class QuizAnswerService extends BaseService<QuizAnswer, QuizAnswerDAO> {
    private QuizAnswerRepo quizAnswerRepo = new QuizAnswerRepo();

    public QuizAnswerService() {
        setBaseRepo(quizAnswerRepo);
        setDAOConversion(new DAOConversion<QuizAnswer, QuizAnswerDAO>() {
            @Override
            public QuizAnswer convertFromDAO(QuizAnswerDAO value) {
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setId(value.getId());
                quizAnswer.setParticipantId(value.getCreatedBy());
                quizAnswer.setQuizItemId(value.getQuizItemId());
                quizAnswer.setTimestamp(new Date(value.getTimestamp()));
                quizAnswer.setSelectedOptionIds(value.getSelectedQuizOptionIds());
                return quizAnswer;
            }

            @Override
            public QuizAnswerDAO convertToDAO(QuizAnswer value) {
                QuizAnswerDAO quizAnswerDAO = new QuizAnswerDAO();
                quizAnswerDAO.setQuizItemId(value.getQuizItemId());
                quizAnswerDAO.setSelectedQuizOptionIds(value.getSelectedOptionIds());
                return quizAnswerDAO;
            }
        });
    }


}
