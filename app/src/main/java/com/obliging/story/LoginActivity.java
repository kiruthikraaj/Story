package com.obliging.story;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText email,password;
    Button login;
    FirebaseAuth firebaseAuth;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        firebaseAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.emailContainer);
        password=findViewById(R.id.passwordContainer);
        login=findViewById(R.id.login_btn);
        signUp=findViewById(R.id.signInView);

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString();
                String passwordid = password.getText().toString();
                if(firebaseAuth.getCurrentUser()==null) {
                    firebaseAuth.signInWithEmailAndPassword(emailId, passwordid).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "NotSuccessFul", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
                else{
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });

       signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finishAffinity();
    }

}