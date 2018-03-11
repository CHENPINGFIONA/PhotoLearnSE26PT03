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
 * Created by yijie on 2018/3/11.
 */

public class UserRepo extends BaseRepo implements IRepository<UserDAO> {

    DatabaseReference userRef = mDatabase.child(ConstHelper.User);

    @Override
    public UserDAO save(UserDAO userDAO) {
        String ID = Integer.toString(userDAO.getId());
        userRef.child(ID).setValue(userDAO);
        return userDAO;
    }

    @Override
    public UserDAO update(UserDAO userDAO) {
        String ID = Integer.toString(userDAO.getId());
        DatabaseReference userById = userRef.child(ID);
        if (userById == null) {
            //log item not found
        } else {
            userById.setValue(userDAO);
        }
        return userDAO;
    }

    @Override
    public boolean delete(UserDAO userDAO) {
        String ID = Integer.toString(userDAO.getId());
        DatabaseReference user = userRef.child(ID);
        if (user == null) {
            //log item not found
            return false;
        } else {
            user.removeValue();
            return true;
        }
    }

    @Override
    public boolean deleteByID(int ID) {
        String idStr = Integer.toString(ID);
        DatabaseReference user = userRef.child(idStr);
        if (user == null) {
            //log item not found
            return false;
        } else {
            user.removeValue();
            return true;
        }
    }

    @Override
    public UserDAO getByID(int ID) {
        String idStr = Integer.toString(ID);
        final List<UserDAO> result = new ArrayList<>();
        userRef.child(idStr).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        UserDAO user = dataSnapshot.getValue(UserDAO.class);
                        if (null != user) {
                            result.add(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        //why use list here? because value inside EventListener can not be directly passed to outside(need final as Modifier)
        return result.get(0);
    }

    @Override
    public Collection<UserDAO> getAll() {
        final List<UserDAO> result = new ArrayList<>();
        userRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            result.add(user.getValue(UserDAO.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }
}
