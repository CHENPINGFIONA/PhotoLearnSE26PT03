package sg.edu.nus.se26pt03.photolearn.fragment;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.LearningItemService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearnigItemDetailFragment extends BaseFragment {
    private LearningItem src=null ;
    private Title title=null;
    private ImageView popupimagebutton;
    private EditText editContentTxtView=null;
    private int role;
    private int mode;
    private String sessionId;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learnig_item_detail, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        src = (LearningItem)this.getArguments().getSerializable(ConstHelper.REF_LEARNING_ITEMS);
        title=src.getTitle();
        sessionId = "1";
        //titleId="-L88Kii8Oc5tSrTBxNaW";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_Access_Mode, AccessMode.toInt(AccessMode.EDIT));
        role = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, UserRole.toInt(UserRole.PARTICIPENT));
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");


        editContentTxtView=(EditText)this.getView().findViewById(R.id.edt_content);
        editContentTxtView.setText(src.getContent());
        ImageView imgPhotView = this.getView().findViewById(R.id.img_photo);
        if(src.getPhotoURL() !=null ) {
            imgPhotView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgPhotView.setAlpha(0.8f);
            try {
                AsyncLoadImageHelper loader = new AsyncLoadImageHelper(imgPhotView);
                loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, src.getPhotoURL());

            } catch (Exception ex) {
                Log.w("adapter", "error");
            }
        }
        popupimagebutton = (ImageView) getView().findViewById(R.id.img_popupmenu);
        Button btnSave=this.getView().findViewById(R.id.btn_item_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Save clicked",Toast.LENGTH_LONG).show();
                LearnigItemDetailFragment.this.src.setContent(editContentTxtView.getText().toString());
                title.createItem(src, new ServiceCallback<Item>() {
                    @Override
                    public void onComplete(Item data) {
                        LearningItem source= (LearningItem) data;
                        Toast.makeText(getContext(),"Save succesfull "+source.getId(),Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate("TitleFragment",0);
                    }

                    @Override
                    public void onError(int code, String message, String details) {
                        Log.w("ERROR",code+"-"+message+"-"+details);

                    }
                });


                /*learningItemService.save(LearnigItemDetailFragment.this.src, new ServiceCallback<LearningItem>() {
                    @Override
                    public void onComplete(LearningItem data) {
                        Toast.makeText(getContext(),"Save succesfull "+data.getId(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(int code, String message, String details) {
                        Log.w("ERROR",code+"-"+message+"-"+details);

                    }
                });*/
                //save logic

               //onBackstack(( src,null);

            }
        });
    }
    @Override
    public void onBackstack(Object object, UserActionCallback callback) {
        getFragmentManager().popBackStackImmediate("TitleFragment",0);
        //super.onBackstack(object, callback);
    }
    @Override
    public void onBefore(Event event, final UserActionCallback callback) {
        new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Your unsaved data will be lost.\n Are you sure you wanted to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getFragmentManager().popBackStackImmediate("TitleFragment",0);
                       // callback.onPass();
                    }})
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onReject();
                    }
                }).show();
    }

    @Override
    public void onResume() {
//        popupimagebutton.setVisibility((mode == AccessMode.toInt(AccessMode.EDIT) && role == UserRole.toInt(UserRole.PARTICIPENT)) ? View.VISIBLE : View.GONE);
        super.onResume();
    }
}
