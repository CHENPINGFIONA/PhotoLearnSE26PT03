package sg.edu.nus.se26pt03.photolearn.database;

import java.util.AbstractMap;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by part time team 3 on 3/11/2018.
 * Modified by part time team 3  on 12/3/2018.
 */

public interface IRepository<T extends BaseDAO> {

    void save(T t, RepoCallback<T> callback);

    void update(T t, RepoCallback<Boolean> callback);

    void delete(T t, RepoCallback<Boolean> callback);

    void deleteById(String id, RepoCallback<Boolean> callback);

    void getById(String id, RepoCallback<T> callback);

    void getAll(RepoCallback<List<T>> callback);

    void getAllByKeyValueList(List<AbstractMap.SimpleEntry<String, Object>> listKeyValue, RepoCallback<List<T>> callback);

    void getAllByKeyValue(String key, Object value, RepoCallback<List<T>> callback);

    void setValueByKey(String key, Object value, RepoCallback<Boolean> callback);
}
