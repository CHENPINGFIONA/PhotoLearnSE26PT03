package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerOptionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 */

public class QuizAnswerOptionRepo extends BaseRepo<QuizAnswerOptionDAO> {

    public QuizAnswerOptionRepo() {
        super(QuizAnswerOptionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_ANSWER_OPTIONS);
    }

    public Collection<QuizAnswerOptionDAO> getAllByQuizAnswerID(final String quizAnswerId) {
        final List<QuizAnswerOptionDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            QuizAnswerOptionDAO quizAnswerOptionDAO = getValue(childDataSnapshot);
                            if (quizAnswerOptionDAO.getQuizAnswerId().equals(quizAnswerId)) {
                                result.add(quizAnswerOptionDAO);
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
