package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 */

public class LearningItemRepo extends BaseRepo implements IRepository<LearningItemDAO> {

    DatabaseReference learningItemRef = mDatabase.child(ConstHelper.LearningItem);

    @Override
    public LearningItemDAO save(LearningItemDAO learningItemDAO) {
        String ID = Integer.toString(learningItemDAO.getId());
        learningItemRef.child(ID).setValue(learningItemDAO);
        return learningItemDAO;
    }

    @Override
    public LearningItemDAO update(LearningItemDAO learningItemDAO) {
        String ID = Integer.toString(learningItemDAO.getId());
        DatabaseReference learningItemById = learningItemRef.child(ID);
        if (learningItemById == null) {
            //log item not found
        } else {
            learningItemById.setValue(learningItemDAO);
        }
        return learningItemDAO;
    }

    @Override
    public boolean delete(LearningItemDAO learningItemDAO) {
        String ID = Integer.toString(learningItemDAO.getId());
        DatabaseReference learningItem = learningItemRef.child(ID);
        if (learningItem == null) {
            //log item not found
            return false;
        } else {
            learningItem.removeValue();
            return true;
        }
    }

    @Override
    public boolean deleteByID(int ID) {
        String idStr = Integer.toString(ID);
        DatabaseReference learningItem = learningItemRef.child(idStr);
        if (learningItem == null) {
            //log item not found
            return false;
        } else {
            learningItem.removeValue();
            return true;
        }
    }

    @Override
    public LearningItemDAO getByID(int ID) {
        String idStr = Integer.toString(ID);
        final List<LearningItemDAO> result = new ArrayList<>();
        learningItemRef.child(idStr).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        LearningItemDAO learningItem = dataSnapshot.getValue(LearningItemDAO.class);
                        if (null != learningItem) {
                            result.add(learningItem);
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
    public Collection<LearningItemDAO> getAll() {
        final List<LearningItemDAO> result = new ArrayList<>();
        learningItemRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningItem : dataSnapshot.getChildren()) {
                            result.add(learningItem.getValue(LearningItemDAO.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }

    public Collection<LearningItemDAO> getAllByLearningTitleID(final int learningTitleID) {
        final List<LearningItemDAO> result = new ArrayList<>();
        learningItemRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningItem : dataSnapshot.getChildren()) {
                            LearningItemDAO learningItemDAO = learningItem.getValue(LearningItemDAO.class);
                            if (learningItemDAO.getTitleId() == learningTitleID) {
                                result.add(learningItem.getValue(LearningItemDAO.class));
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

    public Collection<LearningItemDAO> getAllByLearningTitleIDandCreator(final int learningTitleID, final int userID) {
        final List<LearningItemDAO> result = new ArrayList<>();
        learningItemRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot learningItem : dataSnapshot.getChildren()) {
                            LearningItemDAO learningItemDAO = learningItem.getValue(LearningItemDAO.class);
                            if (learningItemDAO.getCreatedBy() == userID && learningItemDAO.getTitleId() == learningTitleID) {
                                result.add(learningItem.getValue(LearningItemDAO.class));
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
