package sg.edu.nus.se26pt03.photolearn.database;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by MyatMin on 18/3/18.
 */

public interface RepoCallback<T extends BaseDAO> {
    void onRecieved(List<T> data);
}
