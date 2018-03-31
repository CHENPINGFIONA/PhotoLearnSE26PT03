package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizOptionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by part time team 3  on 2018/3/11.
 * Restructured by part time team 3  on 3/12/2018.
 */

public class QuizOptionRepo extends BaseRepo<QuizOptionDAO> {
    public QuizOptionRepo() {
        super(QuizOptionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_OPTIONS);
    }

    public void getAllByQuizItemID(final String quizItemId, RepoCallback<List<QuizOptionDAO>> quizOptionDAORepoCallback) {

        mDatabaseRef.orderByChild("QuizItemId").equalTo(quizItemId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        List<QuizOptionDAO> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            QuizOptionDAO quizOptionDAO = getValue(childDataSnapshot);
                                result.add(quizOptionDAO);
                        }
                        quizOptionDAORepoCallback.onComplete(result);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        quizOptionDAORepoCallback.onError(databaseError);
                    }
                });
    }
}
