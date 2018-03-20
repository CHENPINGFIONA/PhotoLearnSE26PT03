package sg.edu.nus.se26pt03.photolearn.database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    public void getAllByLearningSessionID(final String learningSessionID, final String text, final IListCallback<LearningTitleDAO> iListCallback) {
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<LearningTitleDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = getValue(childDataSnapshot);
                            if (learningTitleDAO.getLearningSessionId().equals(learningSessionID) && learningTitleDAO.getLearningSessionId().contains(text)) {
                                result.add(learningTitleDAO);
                            }
                        }
                        iListCallback.onCallback(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    }

    public void getAllByCreator(final String learningSessionID, final String userId, final IListCallback<LearningTitleDAO> iListCallback) {
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<LearningTitleDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningTitleDAO learningTitleDAO = getValue(childDataSnapshot);
                            if (learningTitleDAO.getCreatedBy().equals(userId) && learningTitleDAO.getLearningSessionId().equals(learningSessionID)) {
                                result.add(learningTitleDAO);
                            }
                        }
                        iListCallback.onCallback(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    }
}