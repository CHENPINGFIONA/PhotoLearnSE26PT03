package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerOptionDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizAnswerOptionRepo extends BaseRepo implements IRepository<QuizAnswerOptionDAO> {


    @Override
    public QuizAnswerOptionDAO save(QuizAnswerOptionDAO quizAnswerOptionDAO) {
        return null;
    }

    @Override
    public QuizAnswerOptionDAO update(QuizAnswerOptionDAO quizAnswerOptionDAO) {
        return null;
    }

    @Override
    public boolean delete(QuizAnswerOptionDAO quizAnswerOptionDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public QuizAnswerOptionDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<QuizAnswerOptionDAO> getAll() {
        return null;
    }
}
