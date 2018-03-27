package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizItemDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class QuizItemRepo extends BaseRepo<QuizItemDAO> {

    public QuizItemRepo() {
        super(QuizItemDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_ITEMS);
    }

    public void getAllByQuizTitleID(final String quizTitleId, final RepoCallback<List<QuizItemDAO>> callback) {
        mDatabaseRef.orderByChild("quizTitleId").equalTo(quizTitleId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<QuizItemDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                            QuizItemDAO quizItemDAO = getValue(childDataSnapshot);
                            result.add(quizItemDAO);

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
