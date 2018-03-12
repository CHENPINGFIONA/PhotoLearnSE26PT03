package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class LearningSessionRepo extends BaseRepo<LearningSessionDAO>{

    public LearningSessionRepo() {
        super(LearningSessionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_LEARNING_SESSIONS);
    }

    public Collection<LearningSessionDAO> getAllByCreator(final String uid) {
        final List<LearningSessionDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningSessionDAO learningSessionDAO = getValue(childDataSnapshot);
                            if (learningSessionDAO.getCreatedBy() == uid) {
                                result.add(learningSessionDAO);
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
