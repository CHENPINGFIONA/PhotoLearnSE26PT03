package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningSessionRepo extends BaseRepo implements IRepository<LearningSessionDAO> {

    @Override
    public LearningSessionDAO save(LearningSessionDAO learningSessionDAO) {
        return null;
    }

    @Override
    public LearningSessionDAO update(LearningSessionDAO learningSessionDAO) {
        return null;
    }

    @Override
    public boolean delete(LearningSessionDAO learningSessionDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public LearningSessionDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<LearningSessionDAO> getAll() {
        return null;
    }
}
