package sg.edu.nus.se26pt03.photolearn.database;

import java.util.Collection;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 * Restructured by MyatMin on 3/12/2018.
 */

public class QuizTitleRepo  extends BaseRepo <QuizTitleDAO> {

    public QuizTitleRepo() {
        super(QuizTitleDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_TITLES);
    }
}
