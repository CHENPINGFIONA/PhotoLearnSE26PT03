package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

/**
 * Created by MyatMin on 12/3/18.
 */

public class BaseDAO {
    protected String Id;
    @Exclude
    public String getId() {
        return Id;
    }

    @Exclude
    public void setId(String id) {
        Id = id;
    }
}
