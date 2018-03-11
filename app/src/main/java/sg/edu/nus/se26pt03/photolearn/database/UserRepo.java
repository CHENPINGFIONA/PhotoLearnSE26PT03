package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.UserDAO;

/**
 * Created by yijie on 2018/3/11.
 */

public class UserRepo extends BaseRepo implements IRepository<UserDAO> {

    @Override
    public UserDAO save(UserDAO userDAO) {
        return null;
    }

    @Override
    public UserDAO update(UserDAO userDAO) {
        return null;
    }

    @Override
    public boolean delete(UserDAO userDAO) {
        return false;
    }

    @Override
    public boolean deleteByID(int ID) {
        return false;
    }

    @Override
    public UserDAO getByID(int ID) {
        return null;
    }

    @Override
    public Collection<UserDAO> getAll() {
        return null;
    }
}
