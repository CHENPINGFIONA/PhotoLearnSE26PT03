package sg.edu.nus.se26pt03.photolearn.database;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public interface ICallback<T> {
    void onCallback(T item);
}
