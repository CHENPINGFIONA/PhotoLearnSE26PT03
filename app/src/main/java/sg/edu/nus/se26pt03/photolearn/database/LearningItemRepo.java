package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 12/3/2018.
 */

public class LearningItemRepo extends BaseRepo<LearningItemDAO> {
    public LearningItemRepo() {
        super(LearningItemDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_LEARNING_ITEMS);
    }

    /*public Collection<LearningItemDAO> getAllByLearningTitleID(final String learningTitleId) {
        final List<LearningItemDAO> result = new ArrayList<>();
        mDatabaseRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningItemDAO learningItemDAO = getValue(childDataSnapshot);
                            if (learningItemDAO.getLearningTitleId().equals(learningTitleId)) {
                                result.add(learningItemDAO);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }*/

   /* public Collection<LearningItemDAO> getAllByLearningTitleIdandCreator(final String learningTitleId, final String uid) {
        final List<LearningItemDAO> result = new ArrayList<>();
        mDatabaseRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningItemDAO learningItemDAO = getValue(childDataSnapshot);
                            if (learningItemDAO.getCreatedBy().equals(uid)
                                    && learningItemDAO.getLearningTitleId().equals(learningTitleId)) {
                                result.add(learningItemDAO);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }*/

    public void getAllByLearningTitleID(final String learningTitleId, final RepoCallback<List<LearningItemDAO>> callback) {
        mDatabaseRef.orderByChild("learningTitleId").equalTo(learningTitleId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<LearningItemDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                            LearningItemDAO learningItemDAO = getValue(childDataSnapshot);
                            result.add(learningItemDAO);

                        }
                        callback.onComplete(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError);
                    }
                });

    }
}
