package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

/**
 * Created by c.banisetty on 3/11/2018.
 * Modified by MyatMin on 12/3/2018.
 */

public interface IRepository<T> {

    T save(T t);
    T update(T t);
    boolean delete(T t);
    boolean deleteById(String id);
    T getById(String id, FireBaseCallback<T> fireBaseCallback);
    Collection<T> getAll();
}
