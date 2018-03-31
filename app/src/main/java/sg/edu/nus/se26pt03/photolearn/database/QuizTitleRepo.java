package sg.edu.nus.se26pt03.photolearn.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by part time team 3  on 2018/3/11.
 * Restructured by part time team 3  on 3/12/2018.
 */

public class QuizTitleRepo extends BaseRepo<QuizTitleDAO> {

    public QuizTitleRepo() {
        super(QuizTitleDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_TITLES);
    }
}
