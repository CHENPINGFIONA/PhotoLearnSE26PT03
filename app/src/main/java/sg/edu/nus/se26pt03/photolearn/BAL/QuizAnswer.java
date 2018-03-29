package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizAnswer {
    private String id;
    private String quizItemId;
    private String participantId;
    private Date timestamp;
    private boolean isCurrentAttempt;
    private List<String> selectedOptionIds;

    private transient List<QuizOption> answerOptions;

    public List<QuizOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<QuizOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizItemId() {
        return quizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        this.quizItemId = quizItemId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getIsCurrentAttempt() {
        return isCurrentAttempt;
    }

    public void setIsCurrentAttempt(boolean isCurrentAttempt) {
        this.isCurrentAttempt = isCurrentAttempt;
    }

    public List<String> getSelectedOptionIds() {
        return selectedOptionIds;
    }

    public void setSelectedOptionIds(List<String> selectedOptionIds) {
        this.selectedOptionIds = selectedOptionIds;
    }
}
