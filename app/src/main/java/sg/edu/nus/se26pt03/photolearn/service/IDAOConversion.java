package sg.edu.nus.se26pt03.photolearn.service;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by part time team 3  on 21/3/18.
 */

public interface IDAOConversion<T, V extends BaseDAO> {
    public T convertFromDAO(V value);
    public V convertToDAO(T value);
    public List<T> convertFromDAO(List<V> list);
    public List<V> convertToDAO(List<T> list);
}
