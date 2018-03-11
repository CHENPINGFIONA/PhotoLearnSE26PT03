package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizItemDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizItemRepo extends BaseRepo implements IRepository<QuizItemDAO> {

    @Override
    public QuizItemDAO save(QuizItemDAO quizItemDAO) {
        return null;
    }

    @Override
    public QuizItemDAO update(QuizItemDAO quizItemDAO) {
        return null;
    }

    @Override
    public boolean delete(QuizItemDAO quizItemDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public QuizItemDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<QuizItemDAO> getAll() {
        return null;
    }
}
