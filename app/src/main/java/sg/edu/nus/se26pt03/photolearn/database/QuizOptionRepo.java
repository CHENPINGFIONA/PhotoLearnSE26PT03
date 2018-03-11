package sg.edu.nus.se26pt03.photolearn.database;


import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizOptionDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizOptionRepo implements IRepository<QuizOptionDAO> {

    @Override
    public QuizOptionDAO save(QuizOptionDAO quizOptionDAO) {
        return null;
    }

    @Override
    public QuizOptionDAO update(QuizOptionDAO quizOptionDAO) {
        return null;
    }

    @Override
    public boolean delete(QuizOptionDAO quizOptionDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public QuizOptionDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<QuizOptionDAO> getAll() {
        return null;
    }

}
