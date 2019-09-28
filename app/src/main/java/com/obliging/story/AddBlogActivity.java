package com.obliging.story;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AddBlogActivity extends AppCompatActivity {
    EditText title,blog;
    Button post;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage ;
    DatabaseReference storyRef,userRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addblog);
        title = findViewById(R.id.blog_title);
        blog = findViewById(R.id.blog_content);
        post = findViewById(R.id.post_btn);

        storageReference = FirebaseStorage.getInstance().getReference();
        storyRef = firebaseDatabase.getInstance().getReference().child("Story");
        FirebaseStorage imageRef = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        final StorageReference dpImageRef = imageRef.getReference().child("ProfileImages").child(firebaseUser.getUid()).child("Images.jpg");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddBlogActivity.this, "Posting", Toast.LENGTH_SHORT).show();
                final String PostTitle = title.getText().toString().trim();
                final String PostContent = blog.getText().toString().trim();


                final String UserName = "Guru";
                dpImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String downloadUri = task.getResult().toString();
                            if (!TextUtils.isEmpty(PostContent)&&(!TextUtils.isEmpty(PostTitle))){
                                final DatabaseReference newPost = storyRef.push();
                                final DatabaseReference userPost = userRef.push();
                                newPost.child("UserName").setValue(UserName);
                                userPost.child("Posts").setValue(newPost.getKey());
                                newPost.child("displayPicture").setValue(downloadUri);
                                newPost.child("title").setValue(PostTitle);
                                newPost.child("blog").setValue(PostContent);
                                newPost.child("uid").setValue(firebaseUser.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            startActivity(new Intent(AddBlogActivity.this,MainActivity.class));
                                        }
                                    }
                                });

                            }
                        }
                    }
                });

            }
        });
    }
}
