package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningItemRepo extends BaseRepo implements IRepository<LearningItemDAO> {

    @Override
    public LearningItemDAO save(LearningItemDAO learningItemDAO) {
        return null;
    }

    @Override
    public LearningItemDAO update(LearningItemDAO learningItemDAO) {
        return null;
    }

    @Override
    public boolean delete(LearningItemDAO learningItemDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public LearningItemDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<LearningItemDAO> getAll() {
        return null;
    }
}
