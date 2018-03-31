package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.UserDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by part time team 3  on 2018/3/11.
 * Restructured by part time team 3  on 12/3/2018.
 */

public class UserRepo extends BaseRepo<UserDAO> {
    public UserRepo() {
        super(UserDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_USERS);
    }
}
