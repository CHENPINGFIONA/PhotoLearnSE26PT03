package sg.edu.nus.se26pt03.photolearn.service;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by part time team 3  on 21/3/18.
 */

public abstract class DAOConversion<T,V extends BaseDAO> implements IDAOConversion<T, V> {
    @Override
    public abstract T convertFromDAO(V value);

    @Override
    public abstract V convertToDAO(T value);

    @Override
    public final List<T> convertFromDAO(List<V> list) {
        List<T> result = new ArrayList<T>();
        for(V value: list) {
            result.add(convertFromDAO(value));
        }
        return result;
    }

    @Override
    public final List<V> convertToDAO(List<T> list) {
        List<V> result = new ArrayList<V>();
        for(T value: list) {
            result.add(convertToDAO(value));
        }
        return result;
    }
}
