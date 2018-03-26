package sg.edu.nus.se26pt03.photolearn.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
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
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by c.banisetty on 3/17/2018.
 */


public class AsyncLoadImageHelper  extends AsyncTask<String, String, Drawable> {
    private final static String TAG = "AsyncLoadImage";
    private final Context context;
    private ImageView imageView;
    private FirebaseStorage storage;
    private Drawable imgDrawable;
    public AsyncLoadImageHelper(ImageView imageView, Context context,ProgressBar progressBar) {
        this.imageView = imageView;
        this.context=context;
        this.progressBar=progressBar;
        storage = FirebaseStorage.getInstance();
       // storageReference = storage.getReference();
    }
    @Override
    protected Drawable doInBackground(String... params) {
        Drawable bitmap = null;
        try {

// Create a new trust manager that trust all certificates
           /* TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

// Activate the new trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }*/
            /*FileStorageHelper fileStorageHelper=new FileStorageHelper();
            fileStorageHelper.GetOutputdownloadFileUrl(params[0], new ServiceCallback<Drawable>() {
                @Override
                public void onComplete(Drawable data) {
                    imgDrawable=data;
                }

                @Override
                public void onError(int code, String message, String details) {

                }
            });*/

            URL url = new URL(params[0]);

            HttpsURLConnection httpConnection=(HttpsURLConnection) url.openConnection();

            httpConnection.connect();

                        InputStream is = (InputStream) httpConnection.getInputStream();
            bitmap = Drawable.createFromStream(is, "src name");
            this.imgDrawable=bitmap;

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
