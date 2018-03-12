package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class QuizAnswerRepo extends BaseRepo<QuizAnswerDAO> {
    public QuizAnswerRepo() {
        super(QuizAnswerDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_ANSWERS);
    }

    public QuizAnswerDAO getByQuizItemIDAndParticipantID(final String quizItemId, final String participantId) {
        final List<QuizAnswerDAO> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            QuizAnswerDAO quizAnswerDAO = getValue(childDataSnapshot);
                            if (quizAnswerDAO.getQuizItemId().equals(quizItemId)
                                    && quizAnswerDAO.getCreatedBy().equals(participantId)) {
                                result.add(quizAnswerDAO);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
