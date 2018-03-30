package sg.edu.nus.se26pt03.photolearn.application;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;

/**
 * Created by MyatMin on 13/3/18.
 */

public interface UserActionListener {
    List<UserActionListener> getRelatives();

    void onBefore(@EventType.Event int event, UserActionCallback callback, UserActionListener source);
    void onAppModeChange(@AppMode.Mode int mode, UserActionCallback callback, UserActionListener source);
    void onAccessModeChange(@AccessMode.Mode int mode, UserActionCallback callback, UserActionListener source);
    void onLogIn(User user, UserActionCallback callback, UserActionListener source);
    void onLogOut(User user, UserActionCallback callback, UserActionListener source);
    void onLoad(Object object, UserActionCallback callback, UserActionListener source);
    void onCreate(Object object, UserActionCallback callback, UserActionListener source);
    void onEdit(Object object, UserActionCallback callback, UserActionListener source);
    void onSummary(Object object, UserActionCallback callback, UserActionListener source);
    void onBackstack(Object object, UserActionCallback callback, UserActionListener source);

    void onBefore(@EventType.Event int event, UserActionCallback callback);
    void onAppModeChange(@AppMode.Mode int mode, UserActionCallback callback);
    void onAccessModeChange(@AccessMode.Mode int mode, UserActionCallback callback);
    void onLogIn(User user, UserActionCallback callback);
    void onLogOut(User user, UserActionCallback callback);

    void onLoad(LearningSession learningSession, UserActionCallback callback);
    void onLoad(LearningTitle learningTitle, UserActionCallback callback);
    void onLoad(LearningItem learningItem, UserActionCallback callback);
    void onLoad(QuizTitle quizTitle, UserActionCallback callback);
    void onLoad(QuizItem quizItem, UserActionCallback callback);
    void onLoad(QuizAnswer quizAnswer, UserActionCallback callback);

    void onCreate(LearningSession learningSession, UserActionCallback callback);
    void onCreate(LearningTitle learningTitle, UserActionCallback callback);
    void onCreate(LearningItem learningItem, UserActionCallback callback);
    void onCreate(QuizTitle quizTitle, UserActionCallback callback);
    void onCreate(QuizItem quizItem, UserActionCallback callback);
    void onCreate(QuizAnswer quizAnswer, UserActionCallback callback);

    void onEdit(LearningSession learningSession, UserActionCallback callback);
    void onEdit(LearningTitle learningTitle, UserActionCallback callback);
    void onEdit(LearningItem learningItem, UserActionCallback callback);
    void onEdit(QuizTitle quizTitle, UserActionCallback callback);
    void onEdit(QuizItem quizItem, UserActionCallback callback);
    void onEdit(QuizAnswer quizAnswer, UserActionCallback callback);

    void onSummary(QuizTitle quizTitle, UserActionCallback callback);

    void onBackstack(Object object, UserActionCallback callback);

}
