package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizTitleRepo  extends BaseRepo implements IRepository<QuizTitleDAO> {

    @Override
    public QuizTitleDAO save(QuizTitleDAO quizTitleDAO) {
        return null;
    }

    @Override
    public QuizTitleDAO update(QuizTitleDAO quizTitleDAO) {
        return null;
    }

    @Override
    public boolean delete(QuizTitleDAO quizTitleDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public QuizTitleDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<QuizTitleDAO> getAll() {
        return null;
    }

}
