package sg.edu.nus.se26pt03.photolearn.database;

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
}
