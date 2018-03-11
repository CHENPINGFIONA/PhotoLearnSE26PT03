package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chen ping on 11/3/2018.
 */

public class BaseRepo implements AutoCloseable {

    protected DatabaseReference mDatabase;

    public BaseRepo() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
    }

    @Override
    public void close() throws Exception {
        FirebaseDatabase.getInstance().goOffline();
    }
}
