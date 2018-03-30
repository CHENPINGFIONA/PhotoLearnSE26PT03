package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizOptionDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningItemRepo;
import sg.edu.nus.se26pt03.photolearn.database.QuizItemRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by c.banisetty on 3/22/2018.
 */

public class QuizItemService extends BaseService<Item, QuizItemDAO> {
    private QuizItemRepo quizItemRepo = new QuizItemRepo();
    private QuizTitle quizTitle;

    public QuizItemService(QuizTitle quizTitle) {
        setBaseRepo(quizItemRepo);
        this.quizTitle = quizTitle;
        setDAOConversion(new DAOConversion<Item, QuizItemDAO>() {
            @Override
            public QuizItem convertFromDAO(QuizItemDAO value) {
                QuizItem result = new QuizItem(quizTitle);
                result.setContent(value.getContent());
                result.setCoordinate(new Coordinate(value.getLatitude(), value.getLongitude()));
                result.setPhotoURL(value.getPhotoURL());
                result.setCreatedBy(value.getCreatedBy());
                result.setId(value.getId());
                if (value.getQuizOptions() != null) {
                    int i = 0;
                    for (QuizOptionDAO optionDAO : value.getQuizOptions()) {
                        QuizOption option = new QuizOption(result);
                        option.setAnswer(optionDAO.isAnswer());
                        option.setContent(optionDAO.getContent());
                        option.setId(Integer.toString(i));
                        result.add(option);
                        i++;
                    }
                }
                result.setPosition(value.getPosition());
                result.setTimestamp(new Date(value.getTimestamp()));
                return result;

            }

            @Override
            public QuizItemDAO convertToDAO(Item value) {
                QuizItemDAO result = new QuizItemDAO();
                QuizItem source = (QuizItem) value;
                result.setContent(source.getContent());
                if (source.getQuizOptions() != null) {
                    int i = 0;
                    for (QuizOption quizOption : source.getQuizOptions()
                            ) {
                        QuizOptionDAO optionDAO = new QuizOptionDAO();
                        optionDAO.setQuizItemId(quizOption.getQuizItemId());
                        optionDAO.setAnswer(quizOption.isAnswer());
                        optionDAO.setContent(quizOption.getContent());
                        result.getQuizOptions().add(optionDAO);
                        i++;

                    }
                }
                result.setPhotoURL(source.getPhotoURL() == null ? "" : source.getPhotoURL());
                result.setCreatedBy(source.getCreatedBy() == null ? "" : source.getCreatedBy());
                result.setQuizTitleId(source.getTitle().getId());
                result.setId(source.getId() == null ? "" : source.getId());
                if(source.getCoordinate()!=null) {
                    result.setLatitude(source.getCoordinate().getLatitude());
                    result.setLongitude(source.getCoordinate().getLongitude());
                }
                result.setTimestamp(source.getTimestamp().getTime());
                return result;
            }
        });
    }

    public void getAllByQuizTitleId(String id, final ServiceCallback<List<Item>> callback) {
        quizItemRepo.getAllByQuizTitleID(id, new RepoCallback<List<QuizItemDAO>>() {
            @Override
            public void onComplete(List<QuizItemDAO> data) {
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


}
