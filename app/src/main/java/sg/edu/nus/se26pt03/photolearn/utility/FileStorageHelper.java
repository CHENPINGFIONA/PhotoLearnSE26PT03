package sg.edu.nus.se26pt03.photolearn.utility;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.xml.sax.DTDHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by part time team 3  on 17/3/18.
 */

public class FileStorageHelper {

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageFilePath = "";
    private Uri imageUrl = null;

    public FileStorageHelper() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    public String uploadFile(Uri uri, final ServiceCallback<String> serviceCallback) {

        String fileName = null;
        if (uri != null) {
            fileName = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("images/" + fileName);
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    imageUrl = taskSnapshot.getDownloadUrl();
                    serviceCallback.onComplete(imageUrl.toString());
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Log.e("Camera", exception.getStackTrace().toString());
                        }
                    });

        }
        return fileName;
    }

    public void downloadFile(File storageDir, String imgUrl, final ServiceCallback<File> callback) throws IOException {

        final File tempImg = createImageFile(storageDir);

        if (imgUrl != null) {
            StorageReference ref = storage.getReferenceFromUrl(imgUrl);
            ref.getFile(tempImg).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    callback.onComplete(tempImg);
                    // Successfully downloaded data to local file
                    // ...
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    callback.onError(1, exception.getMessage(), exception.getLocalizedMessage());
                    // Handle failed download
                    // ...
                }
            });

        }
        //return  tempImg;
    }

    private Drawable drawable = null;
    final long ONE_MEGABYTE = 1024 * 1024;

    public void GetOutputdownloadFileUrl(String imgUrl,final ServiceCallback<Drawable> callback) throws IOException {

        try {
            if (!imgUrl.isEmpty()) {
                StorageReference mStorageRef = storage.getReferenceFromUrl(imgUrl);
                mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        InputStream stream = new ByteArrayInputStream(bytes);
                        drawable = Drawable.createFromStream(stream, "src name");
                        callback.onComplete(drawable);
                        // Data for "images/island.jpg" is returns, use this as needed
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        callback.onError(1, exception.getMessage(), exception.getLocalizedMessage());
                        // Handle failed download
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public File createImageFile(File storageDir) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }


    public Uri getImageUrl() {
        return imageUrl;
    }
}