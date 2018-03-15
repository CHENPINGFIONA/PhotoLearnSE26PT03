package sg.edu.nus.se26pt03.photolearn.BAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.QuizTitleRepo;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.utility.ConvertHelper;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession {
    private int id;
    private Date courseDate;
    private String courseName;
    private String courseCode;
    private int moduleNumber;
    private int createdBy;
    private Date timestamp;

    private List<LearningTitle> learningTitles;
    private List<QuizTitle> quizTitles;
    private LearningTitleRepo learningTitleRepo;
    private QuizTitleRepo quizTitleRepo;

    public LearningSession() {
        learningTitleRepo = new LearningTitleRepo();
        quizTitleRepo = new QuizTitleRepo();
        learningTitles = new ArrayList<LearningTitle>();
        quizTitles = new ArrayList<QuizTitle>();
    }

    public String getLearningSessionId() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return String.format("{0}-{1}-M{2}", df.format(courseDate), courseCode, moduleNumber);
    }

    public void createLearningTitle(LearningTitle title) {
        learningTitleRepo.save(ConvertHelper.toLearningTitleDao(title));
    }

    public void updateLearningTitle(LearningTitle title) {
        learningTitleRepo.update(ConvertHelper.toLearningTitleDao(title));
    }

    public void deleteLearningTitle(LearningTitle title) {
        learningTitleRepo.delete(ConvertHelper.toLearningTitleDao(title));
    }

    public List<LearningTitle> getLearningTitles(String learningSessionId, String mode, String userId, String text) {
        Collection<LearningTitleDAO> learningTitleDAOs;
        if (mode == AccessMode.EDIT.toString()) {
            learningTitleDAOs = learningTitleRepo.getAllByCreator(learningSessionId, userId);
        } else {
            learningTitleDAOs = learningTitleRepo.getAllByLearningSessionID(learningSessionId, text);
        }

        for (LearningTitleDAO learningTitleDAO : learningTitleDAOs) {
            learningTitles.clear();
            learningTitles.add(ConvertHelper.fromLearningTitle(learningTitleDAO));
        }

        return learningTitles;
    }

    public LearningTitle getLearningTitle(int learningTitleId) {
        return new LearningTitle();
    }

    public void createQuizTitle(QuizTitle title) {
        QuizTitleRepo repo = new QuizTitleRepo();
        repo.save(ConvertHelper.toQuizTitleDao(title));
    }

    public void updateQuizTitle(QuizTitle title) {
        quizTitleRepo.update(ConvertHelper.toQuizTitleDao(title));
    }

    public void deleteQuizTitle(QuizTitle title) {
        quizTitleRepo.delete(ConvertHelper.toQuizTitleDao(title));
    }

    public List<QuizTitle> getQuizTitles(String learningSessionId) {
        Collection<QuizTitleDAO> quizTitleDAOs = quizTitleRepo.getAllByLearningSessionID(learningSessionId);

        for (QuizTitleDAO quizTitleDAO : quizTitleDAOs) {
            quizTitles.clear();
            quizTitles.add(ConvertHelper.fromQuizTitle(quizTitleDAO));
        }

        return quizTitles;
    }

    public QuizTitle getQuizTitle(int quizTitleId) {
        return new QuizTitle();
    }
}
