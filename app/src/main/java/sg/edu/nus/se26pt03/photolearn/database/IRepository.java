package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by c.banisetty on 3/11/2018.
 * Modified by MyatMin on 12/3/2018.
 */

public interface IRepository<T extends BaseDAO> {

    void save(T t, RepoCallback<T> callback);

    void update(T t, RepoCallback<Boolean> callback);

    void delete(T t, RepoCallback<Boolean> callback);

    void deleteById(String id, RepoCallback<Boolean> callback);

    void getById(String id, RepoCallback<T> callback);

    void getAll(RepoCallback<List<T>> callback);
}
