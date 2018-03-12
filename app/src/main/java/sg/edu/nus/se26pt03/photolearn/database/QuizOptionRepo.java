package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizOptionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class QuizOptionRepo extends BaseRepo<QuizOptionDAO> {
    public QuizOptionRepo() {
        super(QuizOptionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_OPTIONS);
    }

    public Collection<QuizOptionDAO> getAllByQuizItemID(final String quizItemId) {
        final List<QuizOptionDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            QuizOptionDAO quizOptionDAO = getValue(childDataSnapshot);
                            if (quizOptionDAO.getQuizItemId().equals(quizItemId)) {
                                result.add(quizOptionDAO);
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
