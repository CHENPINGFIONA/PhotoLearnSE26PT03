package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;

/**
 * Created by MyatMin on 18/3/18.
 */

public interface RepoCallback<T> {
    void onComplete(T data);
    void onError(DatabaseError databaseError);
}
