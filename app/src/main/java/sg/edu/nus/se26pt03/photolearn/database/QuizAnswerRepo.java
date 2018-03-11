package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizAnswerRepo implements IRepository<QuizAnswerDAO> {

    @Override
    public QuizAnswerDAO save(QuizAnswerDAO quizAnswerDAO) {
        return null;
    }

    @Override
    public QuizAnswerDAO update(QuizAnswerDAO quizAnswerDAO) {
        return null;
    }

    @Override
    public boolean delete(QuizAnswerDAO quizAnswerDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public QuizAnswerDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<QuizAnswerDAO> getAll() {
        return null;
    }
}
