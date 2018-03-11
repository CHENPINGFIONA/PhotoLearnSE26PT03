package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

/**
 * Created by c.banisetty on 3/11/2018.
 */

public interface IRepository<T> {

    T save(T t);
    T update(T t);
    boolean delete(T t);
    boolean deleteByID(int ID);
    T getByID(int ID);
    Collection<T> getAll();
}
