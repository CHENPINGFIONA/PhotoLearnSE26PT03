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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.R;
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
    private TTSHelper ttsHelper;
    private GPSHelper gpsHelper;
    public static  QuizItemFragment create(int itemNumber ,QuizItem quizItem) {
        QuizItemFragment quizItemFragment = new QuizItemFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemNumber);
        args.putSerializable(ARG_ITEM,quizItem);
        quizItemFragment.setArguments(args);
        return quizItemFragment;
    }
    public QuizItemFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemPosition=getArguments().getInt(ARG_ITEM_COUNT);
        quizItem=(QuizItem) getArguments().getSerializable(ARG_ITEM);
        ttsHelper = new TTSHelper(getContext());
        gpsHelper=new GPSHelper(getContext());
        // this.getActivity().setTitle("Item :"+itemPosition);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_quiz_item, container, false);
        ImageView imgPhotView = rootView.findViewById(R.id.imgItemPhotoUrl);
        TextView txtContentView = rootView.findViewById(R.id.txtConntentView);
        txtContentView.setMovementMethod(new ScrollingMovementMethod());
        TextView txtViewLocation = rootView.findViewById(R.id.txtViewLocation);
        ImageButton btnTTS = rootView.findViewById(R.id.TTSImageButton);
        if (quizItem.getCoordinate() != null) {
            String location = gpsHelper.GetLocationByLatandLongitudeAsString(Double.valueOf(quizItem.getCoordinate().getLatitude()), Double.valueOf(quizItem.getCoordinate().getLongitude()));
            txtViewLocation.setText(location);
        }
        ttsHelper.setTtsButton(btnTTS);
        ttsHelper.setTexttoSpeak(quizItem.getContent());
        try {
            AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, quizItem.getPhotoURL());

        } catch (Exception ex) {
            Log.w("adapter", "error");
        }
        btnTTS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ttsHelper.startandStopTalking();
            }
        });

        txtContentView.setText(quizItem.getContent());

        return rootView;
    }


}
