package sg.edu.nus.se26pt03.photolearn.fragment;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.GPSHelper;
import sg.edu.nus.se26pt03.photolearn.utility.TTSHelper;


/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningItemFragment extends BaseFragment {
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_item, container, false);
    }*/
    public static final String ARG_ITEM_COUNT = "ITEM_COUNT";
    public static final String ARG_ITEM = "ITEM";

    private int itemPosition;
    private LearningItem learningItem;
    private TTSHelper ttsHelper;
    private GPSHelper gpsHelper;
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
        ttsHelper = new TTSHelper(getContext());
       gpsHelper=new GPSHelper(getContext());
        // this.getActivity().setTitle("Item :"+itemPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_learning_item, container, false);
        ImageView imgPhotView = rootView.findViewById(R.id.imgItemPhotoUrl);
        TextView txtContentView = rootView.findViewById(R.id.txtConntentView);
        txtContentView.setMovementMethod(new ScrollingMovementMethod());
        TextView txtViewLocation = rootView.findViewById(R.id.txtViewLocation);
        ImageButton btnTTS = rootView.findViewById(R.id.TTSImageButton);
        if (learningItem.getCoordinate() != null) {
            String location = gpsHelper.GetLocationByLatandLongitudeAsString(Double.valueOf(learningItem.getCoordinate().getLatitude()), Double.valueOf(learningItem.getCoordinate().getLongitude()));
            txtViewLocation.setText(location);
        }
        ttsHelper.setTtsButton(btnTTS);
        ttsHelper.setTexttoSpeak(learningItem.getContent());
        try {
            AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, learningItem.getPhotoURL());

        } catch (Exception ex) {
            Log.w("adapter", "error");
        }
        btnTTS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ttsHelper.startandStopTalking();
            }
        });

        txtContentView.setText(learningItem.getContent());

        return rootView;
    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    @Override
//    public boolean onBefore(Event event) {
//        if (super.onBefore(event)) Log.d("Test", "Stop irriitated TTTTT");
//        return  true;
//    }
//
//    @Override
//    public boolean onBackstack(Object object) {
//
//        if(super.onBackstack(object)) ttsHelper.StopTalking();
//        return true;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        ttsHelper.StopTalking();
//    }

    /*  @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final View view = getView();
        if (view != null) {
          //  getView().getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.width);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


    }*/
}
