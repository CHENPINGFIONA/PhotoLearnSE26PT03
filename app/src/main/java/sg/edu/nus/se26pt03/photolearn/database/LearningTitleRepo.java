package sg.edu.nus.se26pt03.photolearn.database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningTitleRepo extends BaseRepo implements IRepository<LearningTitleDAO> {

    DatabaseReference learningTitleRef = mDatabase.child(ConstHelper.LearningTitle);

    @Override
    public LearningTitleDAO save(LearningTitleDAO learningTitleDAO) {
        String ID = Integer.toString(learningTitleDAO.getId());
        learningTitleRef.child(ID).setValue(learningTitleDAO);
        return learningTitleDAO;
    }

    @Override
    public LearningTitleDAO update(LearningTitleDAO learningTitleDAO) {
        String ID = Integer.toString(learningTitleDAO.getId());
        DatabaseReference learningTitleById = learningTitleRef.child(ID);
        if (learningTitleById == null) {
            //log item not found
        } else {
            learningTitleById.setValue(learningTitleDAO);
        }
        return learningTitleDAO;
    }

    @Override
    public boolean delete(LearningTitleDAO learningTitleDAO) {
        String ID = Integer.toString(learningTitleDAO.getId());
        DatabaseReference learningTitle = learningTitleRef.child(ID);
        if (learningTitle == null) {
            //log item not found
            return false;
        } else {
            learningTitle.removeValue();
            return true;
        }
    }

    @Override
    public boolean deleteByID(int ID) {
        String idStr = Integer.toString(ID);
        DatabaseReference learningTitle = learningTitleRef.child(idStr);
        if (learningTitle == null) {
            //log item not found
            return false;
        } else {
            learningTitle.removeValue();
            return true;
        }
    }

    @Override
    public LearningTitleDAO getByID(int ID) {
        String idStr = Integer.toString(ID);
        final List<LearningTitleDAO> result = new ArrayList<>();
        learningTitleRef.child(idStr).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        LearningTitleDAO learningTitle = dataSnapshot.getValue(LearningTitleDAO.class);
                        if (null != learningTitle) {
                            result.add(learningTitle);
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
    public Collection<LearningTitleDAO> getAll() {
        final List<LearningTitleDAO> result = new ArrayList<>();
        learningTitleRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningTitle : dataSnapshot.getChildren()) {
                            result.add(learningTitle.getValue(LearningTitleDAO.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }

    public Collection<LearningTitleDAO> getAllByLearningSessionID(final int learningSessionID) {
        final List<LearningTitleDAO> result = new ArrayList<>();
        learningTitleRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningTitle : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = learningTitle.getValue(LearningTitleDAO.class);
                            if (learningTitleDAO.getSessionId() == learningSessionID) {
                                result.add(learningTitle.getValue(LearningTitleDAO.class));
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

    public Collection<LearningTitleDAO> getAllByCreator(final int userID) {
        final List<LearningTitleDAO> result = new ArrayList<>();
        learningTitleRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningTitle : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = learningTitle.getValue(LearningTitleDAO.class);
                            if (learningTitleDAO.getCreatedBy() == userID) {
                                result.add(learningTitle.getValue(LearningTitleDAO.class));
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
