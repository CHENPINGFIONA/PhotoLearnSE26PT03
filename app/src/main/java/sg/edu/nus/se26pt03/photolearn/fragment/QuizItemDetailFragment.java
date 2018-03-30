package sg.edu.nus.se26pt03.photolearn.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.databinding.FragmentLearningItemDetailBinding;
import sg.edu.nus.se26pt03.photolearn.databinding.FragmentQuizItemDetailScrollviewBinding;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;
import sg.edu.nus.se26pt03.photolearn.utility.FileStorageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.GPSHelper;
import sg.edu.nus.se26pt03.photolearn.utility.GPSTracker;

/**
 * Created by MyatMin on 08/3/18.
 */
public class QuizItemDetailFragment extends BaseFragment {
    private FragmentQuizItemDetailScrollviewBinding binding;

    private QuizItem src = null;
    private List<QuizOption> quizOptions = null;
    private Title title = null;
    private ImageView popupimagebutton;
    private EditText editContentTxtView, editOption1TxtView, editOption2TxtView, editOption3TxtView, editOption4TxtView;
    private CheckBox chkOption1, chkOption2, chkOption3, chkOption4;
    private TextView tv_location;
    private int role;
    private int mode;
    private String sessionId;
    private String userId;
    private GPSTracker gpsTracker;
    private ImageButton captureImageButton;
    private GPSHelper gpsHelper;

    private ImageView imgPhotView;
    FileStorageHelper mStorageHelper;
    ProgressBar progressBar;

    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private String imageFilePath = "";
    private QuizItem srcCopy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gpsTracker = new GPSTracker(getActivity());
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.requestpermission();
        }
        gpsHelper = new GPSHelper(getContext());
        src = (QuizItem) this.getArguments().getSerializable(ConstHelper.REF_QUIZ_ITEMS);
        title = src.getTitle();
        quizOptions = src.getQuizOptions();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_item_detail_scrollview, container, false);
        View view = binding.getRoot();
        setupData(binding);
        // return inflater.inflate(R.layout.fragment_quiz_item_detail_scrollview, container, false);
        return view;
    }

    private void setupData(FragmentQuizItemDetailScrollviewBinding binding) {
        try {
            src = (QuizItem) this.getArguments().getSerializable(ConstHelper.REF_QUIZ_ITEMS);
            title = src.getTitle();
            srcCopy = src.clone();
        } catch (CloneNotSupportedException e) {
            Log.d(this.getClass().getSimpleName(), e.getMessage());
        }
        binding.setQuizItem(src);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupControls();
        setupViews();
    }

    private void setupViews() {
    }


    private void setupControls() {

        tv_location = (TextView) this.getView().findViewById(R.id.tv_location);
        if (src.getCoordinate() != null) {
            tv_location.setText(gpsHelper.GetLocationByLatandLongitudeAsString(src.getCoordinate().getLatitude(), src.getCoordinate().getLongitude()));
        }

        progressBar = (ProgressBar) this.getView().findViewById(R.id.pb_imgloadprogressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imgPhotView = this.getView().findViewById(R.id.img_photo);
        if (src.getPhotoURL() != null) {
            imgPhotView.setAlpha(0.8f);
            try {
                AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView, getContext(), progressBar);
                loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, src.getPhotoURL());

            } catch (Exception ex) {
                Log.w("adapter", "error");
            }
        }
        //Question
        editContentTxtView = (EditText) this.getView().findViewById(R.id.et_content);
        editContentTxtView.setText(src.getContent());
        //option content
        editOption1TxtView = (EditText) this.getView().findViewById(R.id.et_option_content1);
        editOption2TxtView = (EditText) this.getView().findViewById(R.id.et_option_content2);
        editOption3TxtView = (EditText) this.getView().findViewById(R.id.et_option_content3);
        editOption4TxtView = (EditText) this.getView().findViewById(R.id.et_option_content4);

        //checkboxs
        chkOption1 = (CheckBox) this.getView().findViewById(R.id.chk_option1);
        chkOption2 = (CheckBox) this.getView().findViewById(R.id.chk_option2);
        chkOption3 = (CheckBox) this.getView().findViewById(R.id.chk_option3);
        chkOption4 = (CheckBox) this.getView().findViewById(R.id.chk_option4);
        //edit and delete button
        popupimagebutton = (ImageView) getView().findViewById(R.id.img_popupmenu);
        setupCheckBoxes(quizOptions);

        //save button
        Button btnSave = this.getView().findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Save clicked", Toast.LENGTH_LONG).show();
                QuizItemDetailFragment.this.src.setContent(editContentTxtView.getText().toString());

                ServiceCallback<Item> itemServiceCallback = new SaveServiceCallback();
                ServiceCallback<Boolean> booleanServiceCallback = new UpdateServiceCallback();
                if (src.getId() == null || src.getId().equals("")) {
                    title.createItem(src, itemServiceCallback);
                } else {
                    setupOptions(src);
                    title.updateItem(src, booleanServiceCallback);
                }
            }
        });

        //File Storage setup
        mStorageHelper = new FileStorageHelper();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        //capture image
        captureImageButton = (ImageButton) this.getView().findViewById(R.id.imgbtn_CaptureImage);
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCameraIntent();
            }
        });
    }
    private void setupCheckBoxes(List<QuizOption> quizOptions){
        if (quizOptions != null) {
            if (quizOptions.size() > 0 && quizOptions.size() <= 4) {
                //set option values
                QuizOption quizOption1 = quizOptions.get(0);
                editOption1TxtView.setText(quizOption1.getContent());
                chkOption1.setChecked(quizOption1.isAnswer());
                QuizOption quizOption2 = quizOptions.get(1);
                editOption2TxtView.setText(quizOption2.getContent());
                chkOption2.setChecked(quizOption2.isAnswer());
                QuizOption quizOption3 = quizOptions.get(2);
                editOption3TxtView.setText(quizOption3.getContent());
                chkOption3.setChecked(quizOption3.isAnswer());
                QuizOption quizOption4 = quizOptions.get(3);
                editOption4TxtView.setText(quizOption4.getContent() + " " + quizOption4.isAnswer());
                chkOption4.setChecked(quizOption4.isAnswer());
            }
        }
    }
    private void setupOptions(QuizItem source) {
        source.getQuizOptions().clear();
        addOptionToItem(source, 0, editOption1TxtView.getText().toString(), chkOption1.isChecked());
        addOptionToItem(source, 1, editOption2TxtView.getText().toString(), chkOption2.isChecked());
        addOptionToItem(source, 2, editOption3TxtView.getText().toString(), chkOption3.isChecked());
        addOptionToItem(source, 3, editOption4TxtView.getText().toString(), chkOption4.isChecked());
    }
    private void addOptionToItem(QuizItem item, int position, String content, boolean answer) {
        QuizOption option1 = new QuizOption(item);
        option1.setContent(editOption1TxtView.getText().toString());
        option1.setAnswer(chkOption1.isChecked());
        item.update(position, option1);
    }
    public class SaveServiceCallback implements ServiceCallback<Item> {
        @Override
        public void onComplete(Item data) {

            QuizItem source = (QuizItem) data;
            setupOptions(source);

            title.updateItem(source, new UpdateServiceCallback());
        }

        @Override
        public void onError(int code, String message, String details) {
            Log.w("ERROR", code + "-" + message + "-" + details);

        }
    }
    public class UpdateServiceCallback implements ServiceCallback<Boolean> {
        @Override
        public void onComplete(Boolean data) {
            if (data) {
                getFragmentManager().popBackStackImmediate("QuizFragment"+title.getId(), 0);
            } else {
                Toast.makeText(getContext(), "Udpdate unsuccessful Please try later", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onError(int code, String message, String details) {
            Log.w("ERROR", code + "-" + message + "-" + details);

        }
    }
    private void OpenCameraIntent() {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(this.getActivity().getPackageManager()) != null) {

            File photoFile, storageDir = null;
            try {
                storageDir = this.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                photoFile = mStorageHelper.createImageFile(storageDir);
                imageFilePath = photoFile.getAbsolutePath();
                // PackageManager packageManager=this.getActivity().getPackageManager();
                Toast.makeText(getContext(), getContext().getPackageName(), Toast.LENGTH_LONG);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }
    @Override
    public void onBackstack(Object object, UserActionCallback callback) {
        getFragmentManager().popBackStackImmediate("QuizFragment"+title.getId(), 0);
    }
    @Override
    public void onBefore(@EventType.Event int event, final UserActionCallback callback) {
        new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Your unsaved data will be lost.\n Are you sure you wanted to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getFragmentManager().popBackStackImmediate("QuizFragment"+title.getId(), 0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onReject();
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                imgPhotView.setImageURI(Uri.parse(imageFilePath));
                Toast.makeText(getContext(), "Uploading..", Toast.LENGTH_SHORT).show();

                if (gpsTracker.canGetLocation()) {
                    Location location = gpsTracker.getLocation();
                    if (location != null) {
                        Coordinate coordinate = new Coordinate(location.getLatitude(), location.getLongitude());
                        src.setCoordinate(coordinate);
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
            Uri file = Uri.fromFile(new File(imageFilePath));
            String fN = mStorageHelper.uploadFile(file, new ServiceCallback<String>() {

                @Override
                public void onComplete(String data) {
                    src.setPhotoURL(data);

                }

                @Override
                public void onError(int code, String message, String details) {
                    Log.e("", "");
                }
            });


        }
    }
}
