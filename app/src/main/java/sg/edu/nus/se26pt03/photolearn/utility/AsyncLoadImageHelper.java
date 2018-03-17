package sg.edu.nus.se26pt03.photolearn.utility;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by c.banisetty on 3/17/2018.
 */


public class AsyncLoadImageHelper  extends AsyncTask<String, String, Drawable> {
    private final static String TAG = "AsyncLoadImage";
    private ImageView imageView;
    public AsyncLoadImageHelper(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    protected Drawable doInBackground(String... params) {
        Drawable bitmap = null;
        try {
            URL url = new URL(params[0]);
            InputStream is = (InputStream) url.getContent();
            bitmap = Drawable.createFromStream(is, "src name");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Drawable bitmap) {
        imageView.setImageDrawable(bitmap);
    }
}
