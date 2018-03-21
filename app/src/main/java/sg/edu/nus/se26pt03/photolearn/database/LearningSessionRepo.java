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

public class LearningSessionRepo extends BaseRepo<LearningSessionDAO> {

    public LearningSessionRepo() {
        super(LearningSessionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_LEARNING_SESSIONS);
    }

    public void getAllByCreator(final String uid, final RepoCallback<List<LearningSessionDAO>> callback) {
        mDatabaseRef.orderByChild("createdBy").equalTo(uid).addListenerForSingleValueEvent(
        new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<LearningSessionDAO> result = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    LearningSessionDAO learningSessionDAO = getValue(childDataSnapshot);
                    result.add(learningSessionDAO);
                }
                callback.onComplete(result);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public void getByLearningSessionID(final String uid, final RepoCallback<List<LearningSessionDAO>> callback) {
        mDatabaseRef.orderByChild("createdBy").equalTo(uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<LearningSessionDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            LearningSessionDAO learningSessionDAO = getValue(childDataSnapshot);
                            result.add(learningSessionDAO);
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
