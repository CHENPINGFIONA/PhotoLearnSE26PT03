package sg.edu.nus.se26pt03.photolearn.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import sg.edu.nus.se26pt03.photolearn.R;

public class PhotoLearnAppExceptionActivity extends AppCompatActivity {

    TextView tv_errormesasge;
    String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        errorMessage = getIntent().getExtras().getString("error");

        setContentView(R.layout.activity_photo_learn_app_exception);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);
        tv_errormesasge = view.findViewById(R.id.tv_errormessage);
        tv_errormesasge.setText(errorMessage);
        return view;
    }
}
