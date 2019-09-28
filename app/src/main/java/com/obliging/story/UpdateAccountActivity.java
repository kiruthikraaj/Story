package com.obliging.story;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;



public class UpdateAccountActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    final String userid=firebaseAuth.getCurrentUser().getUid();
    ImageView dp;
    Button nxt;
    EditText usrName;
    StorageReference imgref;
    FloatingActionButton add;
    private Uri uri;
    private final int gallerycode=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        dp = findViewById(R.id.displayPicture);
        add = findViewById(R.id.dp_Add);
        usrName = findViewById(R.id.userName_Container);
        nxt = findViewById(R.id.next);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent galleryPick = new Intent();
               galleryPick.setAction(Intent.ACTION_GET_CONTENT);
               galleryPick.setType("image/*");
               startActivityForResult(galleryPick,gallerycode);
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserName = usrName.getText().toString();
                if (UserName.isEmpty()){
                    usrName.setError("Pick A UserName");
                }
                else {
                    firebaseDatabase.getReference("Users").child(userid).child("UserName").setValue(UserName);
                    startActivity(new Intent(UpdateAccountActivity.this,MainActivity.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==gallerycode && resultCode==RESULT_OK && data!=null){
            uri = data.getData();
            CropImage.activity(uri).setAspectRatio(1,1).setAutoZoomEnabled(true).start(this);
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri=result.getUri();

            Picasso.with(this).load(uri).into(dp);
            imgref = firebaseStorage.getReference().child("ProfileImages").child(userid).child("/Images.jpg");
            Task<Uri> urlTask = imgref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    Toast.makeText(UpdateAccountActivity.this, "Dp Upload Success", Toast.LENGTH_SHORT).show();

                    // Continue with the task to get the download URL
                    return imgref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Toast.makeText(UpdateAccountActivity.this, ""+downloadUri.toString(), Toast.LENGTH_SHORT).show();
                        firebaseDatabase.getReference("Users").child(userid).child("displayPicture").setValue(downloadUri.toString());
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

            imgref.getDownloadUrl();
        }
    }
}
