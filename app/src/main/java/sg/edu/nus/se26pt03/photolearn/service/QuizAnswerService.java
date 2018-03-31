package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerDAO;
import sg.edu.nus.se26pt03.photolearn.database.QuizAnswerRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by part time team 3  on 2018/3/24.
 */

public class QuizAnswerService extends BaseService<QuizAnswer, QuizAnswerDAO> {
    private QuizAnswerRepo quizAnswerRepo = new QuizAnswerRepo();

    public QuizAnswerService() {
        setBaseRepo(quizAnswerRepo);
        setDAOConversion(new DAOConversion<QuizAnswer, QuizAnswerDAO>() {
            @Override
            public QuizAnswer convertFromDAO(QuizAnswerDAO value) {
                if (value == null) return null;
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setId(value.getId());
                quizAnswer.setParticipantId(value.getCreatedBy());
                quizAnswer.setQuizItemId(value.getQuizItemId());
                quizAnswer.setIsCurrentAttempt(value.getIsCurrentAttempt());
                quizAnswer.setTimestamp(new Date(value.getTimestamp()));
                quizAnswer.setSelectedOptionIds(value.getSelectedQuizOptionIds());
                return quizAnswer;
            }

            @Override
            public QuizAnswerDAO convertToDAO(QuizAnswer value) {
                if (value == null) return null;
                QuizAnswerDAO quizAnswerDAO = new QuizAnswerDAO();
                quizAnswerDAO.setId(value.getId());
                quizAnswerDAO.setQuizItemId(value.getQuizItemId());
                quizAnswerDAO.setIsCurrentAttempt(value.getIsCurrentAttempt());
                quizAnswerDAO.setSelectedQuizOptionIds(value.getSelectedOptionIds());
                return quizAnswerDAO;
            }
        });
    }

    public void getByQuizItemIDAndParticipantID(String quizItemId, String participantId, final ServiceCallback<QuizAnswer> callback){
        quizAnswerRepo.getByQuizItemIDAndParticipantID(quizItemId, participantId, new RepoCallback<QuizAnswerDAO>(){
            @Override
            public void onComplete(QuizAnswerDAO data) {
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void getCurrentAttemptByQuizItemIDAndParticipantID(String participantId, List<String> quizItemIds, final ServiceCallback<QuizAnswer> callback) {
        quizAnswerRepo.getCurrentAttemptByQuizItemIDAndParticipantID(participantId, quizItemIds, new RepoCallback<QuizAnswerDAO>() {
            @Override
            public void onComplete(QuizAnswerDAO data) {
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });

    }
}
