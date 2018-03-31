package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.activity.LearningActivity;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.GPSHelper;
import sg.edu.nus.se26pt03.photolearn.utility.TTSHelper;


/**
 * Created by part time team 3  on 08/3/18.
 */
public class LearningItemFragment extends BaseFragment {

    public static final String ARG_ITEM_COUNT = "ITEM_COUNT";
    public static final String ARG_ITEM = "ITEM";


    private int itemPosition;

    public LearningItem getLearningItem() {
        return learningItem;
    }

    //Data
    private LearningItem learningItem;
    //helpers
    //private TTSHelper ttsHelper;
    private GPSHelper gpsHelper;
    //controls
    private TextView txtContentView,txtViewLocation;
    private ImageButton btnTTS;
    private ProgressBar progressBar;
    private ImageView imgPhotView;

    public static  LearningItemFragment create(int itemNumber ,LearningItem learningItem) {
        LearningItemFragment learningItemSlideFragment = new LearningItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemNumber);
        args.putSerializable(ARG_ITEM,learningItem);
        learningItemSlideFragment.setArguments(args);
        return learningItemSlideFragment;
    }
    public LearningItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemPosition=getArguments().getInt(ARG_ITEM_COUNT);
        learningItem=(LearningItem) getArguments().getSerializable(ARG_ITEM);
       // ttsHelper = new TTSHelper(getContext());
        gpsHelper=new GPSHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_learning_item, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupControl();
        setupView();
    }

    void setupControl(){
        //Get Controls
        imgPhotView = getView().findViewById(R.id.img_PhotoUrl);
        txtContentView = getView().findViewById(R.id.tv_contentview);
        txtContentView.setMovementMethod(new ScrollingMovementMethod());
        txtViewLocation = getView().findViewById(R.id.tv_viewitemlocation);
        btnTTS = getView().findViewById(R.id.img_ttsbutton);
        progressBar =getView().findViewById(R.id.pb_imgloadprogressBar);

        //adding relavent details to the controls
        txtContentView.setText(learningItem.getContent());
        //location setup
        if (learningItem.getCoordinate() != null) {
            String location = gpsHelper.GetLocationByLatandLongitudeAsString(learningItem.getCoordinate().getLatitude(), learningItem.getCoordinate().getLongitude());
            txtViewLocation.setText(location);
        }
        //TTS setup
        //ttsHelper.setTtsButton(btnTTS);

        btnTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LearningActivity.ttsHelper!=null && LearningActivity.ttsHelper.getTexttoSpeak().equals(learningItem.getContent())){
                    LearningActivity.ttsHelper.stopTalking();
                }
                else {

                    LearningActivity.ttsHelper.stopTalking();
                    LearningActivity.ttsHelper.setTexttoSpeak(learningItem.getContent());
                    LearningActivity.ttsHelper.startandStopTalking();
                }
            }
        });

        //image loading based on Url
        try {
            AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView,getContext(),progressBar);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, learningItem.getPhotoURL());//"http://i63.tinypic.com/2yjzcrr.jpg");

        } catch (Exception ex) {
            Log.w("adapter", "error");
        }




    }
    void setupView(){
        if(learningItem.getCreatedBy() == App.getCurrentUser().getId()){

        }
    }

    @Override
    public void onBackstack(Object object, UserActionCallback callback) {
        LearningActivity.ttsHelper.stopTalking();
        super.onBackstack(object, callback);
    }

    @Override
    public void onPause() {
        LearningActivity.ttsHelper.stopTalking();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        LearningActivity.ttsHelper.stopTalking();
        super.onDestroy();
    }

}
