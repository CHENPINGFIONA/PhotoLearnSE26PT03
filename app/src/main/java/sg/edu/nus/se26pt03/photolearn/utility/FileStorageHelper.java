package sg.edu.nus.se26pt03.photolearn.utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * Created by pradeep on 17/3/18.
 */

public class FileStorageHelper {

    private FirebaseStorage storage;
    private StorageReference storageReference;

    FileStorageHelper()
    {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    private String uploadFile(Uri uri) {

        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
        String fileName=null;
        if(uri != null)
        {
            fileName = UUID.randomUUID().toString() ;
            StorageReference ref = storageReference.child("images/"+ fileName);
            ref.putFile(uri);

        }
        return  fileName;
    }

    private Uri downloadFile(String fileName) {

        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
        Uri uri=null;

        if(fileName != null)
        {
            StorageReference ref = storageReference.child("images/"+ fileName);
            ref.getFile(uri);

        }
        return  uri;
    }

}
