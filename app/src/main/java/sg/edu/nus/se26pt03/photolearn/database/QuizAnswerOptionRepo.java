package sg.edu.nus.se26pt03.photolearn.database;

import sg.edu.nus.se26pt03.photolearn.DAL.QuizAnswerOptionDAO;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by yijie on 2018/3/11.
 *
 */

public class QuizAnswerOptionRepo extends BaseRepo<QuizAnswerOptionDAO> {

    public QuizAnswerOptionRepo() {
        super(QuizAnswerOptionDAO.class);
        mDatabaseRef = mDatabaseRef.child(ConstHelper.REF_QUIZ_ANSWER_OPTIONS);
    }
}
