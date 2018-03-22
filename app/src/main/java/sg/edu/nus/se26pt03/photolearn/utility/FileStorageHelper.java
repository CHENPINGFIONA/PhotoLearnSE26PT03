package sg.edu.nus.se26pt03.photolearn.utility;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by pradeep on 17/3/18.
 */

public class FileStorageHelper {

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageFilePath = "";

    public FileStorageHelper()
    {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    public String uploadFile(Uri uri) {

        String fileName=null;
        if(uri != null)
        {
            fileName = UUID.randomUUID().toString() ;
            StorageReference ref = storageReference.child("images/"+ fileName);
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });

        }
        return  fileName;
    }

    public File downloadFile(File storageDir,String fileName) throws IOException{

        File tempImg = createImageFile(storageDir);

        if(fileName != null)
        {
            StorageReference ref = storageReference.child("images/"+ fileName);
            ref.getFile(tempImg).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Successfully downloaded data to local file
                    // ...
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        }
        return  tempImg;
    }

    public File createImageFile(File storageDir) throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }


}