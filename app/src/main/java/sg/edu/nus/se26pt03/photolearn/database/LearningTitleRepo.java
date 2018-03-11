package sg.edu.nus.se26pt03.photolearn.database;


import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningTitleRepo implements IRepository<LearningTitleDAO> {

    @Override
    public LearningTitleDAO save(LearningTitleDAO learningTitleDAO) {
        return null;
    }

    @Override
    public LearningTitleDAO update(LearningTitleDAO learningTitleDAO) {
        return null;
    }

    @Override
    public boolean delete(LearningTitleDAO learningTitleDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public LearningTitleDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<LearningTitleDAO> getAll() {
        return null;
    }

}
