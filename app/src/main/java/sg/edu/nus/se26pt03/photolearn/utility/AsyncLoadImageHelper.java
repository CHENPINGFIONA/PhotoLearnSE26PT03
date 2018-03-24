package sg.edu.nus.se26pt03.photolearn.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by c.banisetty on 3/17/2018.
 */


public class AsyncLoadImageHelper  extends AsyncTask<String, String, Drawable> {
    private final static String TAG = "AsyncLoadImage";
    private final Context context;
    private ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public AsyncLoadImageHelper(ImageView imageView, Context context,ProgressBar progressBar) {
        this.imageView = imageView;
        this.context=context;
        this.progressBar=progressBar;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
    @Override
    protected Drawable doInBackground(String... params) {
        Drawable bitmap = null;
        try {


            URL url = new URL(params[0]);
            InputStream is = (InputStream) url.getContent();
            bitmap = Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }

    private ProgressBar progressBar;

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        //progressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
            // To Hide ProgressBar
    }



    @Override
    protected void onPostExecute(Drawable bitmap) {
        imageView.setImageDrawable(bitmap);
        super.onPostExecute(bitmap);
        progressBar.setVisibility(View.INVISIBLE);

    }
}
