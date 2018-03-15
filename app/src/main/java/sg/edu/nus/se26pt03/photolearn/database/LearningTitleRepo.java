package sg.edu.nus.se26pt03.photolearn.database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class LearningTitleRepo extends BaseRepo<LearningTitleDAO> {
    public LearningTitleRepo() {
        super(LearningTitleDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_LEARNING_TITLES);
    }

    public Collection<LearningTitleDAO> getAllByLearningSessionID(final String learningSessionID, final String text) {
        final List<LearningTitleDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = getValue(childDataSnapshot);
                            if (learningTitleDAO.getLearningSessionId().equals(learningSessionID) && learningTitleDAO.getLearningSessionId().contains(text)) {
                                result.add(learningTitleDAO);
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

    public Collection<LearningTitleDAO> getAllByCreator(final String learningSessionID, final String userId) {
        final List<LearningTitleDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = getValue(childDataSnapshot);
                            if (learningTitleDAO.getCreatedBy().equals(userId) && learningTitleDAO.getLearningSessionId().equals(learningSessionID)) {
                                result.add(learningTitleDAO);
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