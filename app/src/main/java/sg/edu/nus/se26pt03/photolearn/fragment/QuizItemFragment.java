package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.GPSHelper;
import sg.edu.nus.se26pt03.photolearn.utility.TTSHelper;

/**
 * Created by MyatMin on 08/3/18.
 */
public class QuizItemFragment extends BaseFragment {


    public static final String ARG_ITEM_COUNT = "ITEM_COUNT";
    public static final String ARG_ITEM = "ITEM";

    private int itemPosition;
    private QuizItem quizItem;
    private GPSHelper gpsHelper;
    private QuizTitle quizTitle;
    private CheckBox chk_opt1, chk_opt2, chk_opt3, chk_opt4;


    public static QuizItemFragment create(int itemNumber, QuizItem quizItem) {
        QuizItemFragment quizItemFragment = new QuizItemFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemNumber);
        args.putSerializable(ARG_ITEM, quizItem);
        quizItemFragment.setArguments(args);
        return quizItemFragment;
    }

    public QuizItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemPosition = getArguments().getInt(ARG_ITEM_COUNT);
        quizItem = (QuizItem) getArguments().getSerializable(ARG_ITEM);
        gpsHelper = new GPSHelper(getContext());
        // this.getActivity().setTitle("Item :"+itemPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_quiz_item, container, false);


        return rootView;
    }

    ImageView imgPhotView;
    TextView txtContentView;
    TextView txtViewLocation;
    ProgressBar progressBar;

    private void setupControl() {

        imgPhotView = getView().findViewById(R.id.imgItemPhotoUrl);
        txtContentView = getView().findViewById(R.id.tv_contentview);
        txtContentView.setMovementMethod(new ScrollingMovementMethod());
        txtViewLocation = getView().findViewById(R.id.tv_location);

        progressBar = getView().findViewById(R.id.quizitemListprogressBarSmall);


        if (quizItem.getCoordinate() != null) {
            String location = gpsHelper.GetLocationByLatandLongitudeAsString(Double.valueOf(quizItem.getCoordinate().getLatitude()), Double.valueOf(quizItem.getCoordinate().getLongitude()));
            txtViewLocation.setText(location);
        }
        try {
            AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView, getContext(), progressBar);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, quizItem.getPhotoURL());

        } catch (Exception ex) {
            Log.w("adapter", "error");
        }

        txtContentView.setText(quizItem.getContent());
        chk_opt1 = getView().findViewById(R.id.chk_option1);
        chk_opt2 = getView().findViewById(R.id.chk_option2);
        chk_opt3 = getView().findViewById(R.id.chk_option3);
        chk_opt4 = getView().findViewById(R.id.chk_option4);
        if (quizItem.getQuizOptions() != null && quizItem.getQuizOptions().size() > 0) {
            QuizOption quizOption1 = quizItem.getQuizOptions().get(1);
            chk_opt1.setText(quizOption1.getContent());
            QuizOption quizOption2 = quizItem.getQuizOptions().get(2);
            chk_opt2.setText(quizOption2.getContent());
            QuizOption quizOption3 = quizItem.getQuizOptions().get(3);
            chk_opt3.setText(quizOption3.getContent());
            QuizOption quizOption4 = quizItem.getQuizOptions().get(4);
            chk_opt4.setText(quizOption4.getContent());
        }

    }

    void setupView() {
        if (UserRole.TRAINER.equals(App.currentAppMode)) {
            chk_opt1.setEnabled(false);
            chk_opt2.setEnabled(false);
            chk_opt3.setEnabled(false);
            chk_opt4.setEnabled(false);
        }
    }
}
