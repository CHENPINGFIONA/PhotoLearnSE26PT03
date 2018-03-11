package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningSessionRepo extends BaseRepo implements IRepository<LearningSessionDAO> {

    DatabaseReference learningSessionRef = mDatabase.child(ConstHelper.LearningSession);

    @Override
    public LearningSessionDAO save(LearningSessionDAO learningSessionDAO) {
        String ID = Integer.toString(learningSessionDAO.getId());
        learningSessionRef.child(ID).setValue(learningSessionDAO);
        return learningSessionDAO;
    }

    @Override
    public LearningSessionDAO update(LearningSessionDAO learningSessionDAO) {
        String ID = Integer.toString(learningSessionDAO.getId());
        DatabaseReference learningSessionById = learningSessionRef.child(ID);
        if (learningSessionById == null) {
            //log item not found
        } else {
            learningSessionById.setValue(learningSessionDAO);
        }
        return learningSessionDAO;
    }

    @Override
    public boolean delete(LearningSessionDAO learningSessionDAO) {
        String ID = Integer.toString(learningSessionDAO.getId());
        DatabaseReference learningSession = learningSessionRef.child(ID);
        if (learningSession == null) {
            //log item not found
            return false;
        } else {
            learningSession.removeValue();
            return true;
        }
    }

    @Override
    public boolean deleteByID(int ID) {
        String idStr = Integer.toString(ID);
        DatabaseReference learningSession = learningSessionRef.child(idStr);
        if (learningSession == null) {
            //log item not found
            return false;
        } else {
            learningSession.removeValue();
            return true;
        }
    }

    @Override
    public LearningSessionDAO getByID(int ID) {
        String idStr = Integer.toString(ID);
        final List<LearningSessionDAO> result = new ArrayList<>();
        learningSessionRef.child(idStr).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        LearningSessionDAO learningSession = dataSnapshot.getValue(LearningSessionDAO.class);
                        if (null != learningSession) {
                            result.add(learningSession);
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
    public Collection<LearningSessionDAO> getAll() {
        final List<LearningSessionDAO> result = new ArrayList<>();
        learningSessionRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningSession : dataSnapshot.getChildren()) {
                            result.add(learningSession.getValue(LearningSessionDAO.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }

    public Collection<LearningSessionDAO> getAllByCreator(final int userID) {
        final List<LearningSessionDAO> result = new ArrayList<>();
        learningSessionRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningSession : dataSnapshot.getChildren()) {
                            LearningSessionDAO learningSessionDAO = learningSession.getValue(LearningSessionDAO.class);
                            if (learningSessionDAO.getCreatedBy() == userID) {
                                result.add(learningSession.getValue(LearningSessionDAO.class));
                            }
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
