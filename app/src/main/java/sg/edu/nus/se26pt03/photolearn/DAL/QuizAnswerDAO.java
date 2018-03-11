package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;
/**
 * Created by chen ping on 11/3/2018.
 */

public class QuizAnswerDAO {
    private int Id;
    private int QuizItemId;
    private int ParticipantId;
    private Date Timestamp;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuizItemId() {
        return QuizItemId;
    }

    public void setQuizItemId(int quizItemId) {
        QuizItemId = quizItemId;
    }

    public int getParticipantId() {
        return ParticipantId;
    }

    public void setParticipantId(int participantId) {
        ParticipantId = participantId;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
