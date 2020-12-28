package com.example.diet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button signup,signin;
    private TextView editMail,editPassword;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMail=findViewById(R.id.mailid);
        editPassword=findViewById(R.id.pw);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        signin=findViewById(R.id.signin);
        signin.setOnClickListener(this);
        mauth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin:
                login();
                break;
            case R.id.signup:
                startActivity(new Intent(MainActivity.this, Signup.class));
                break;
        }
    }
    private void login(){
        String mail=editMail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        if(mail.isEmpty()){
            editMail.setError("Email is required");
            editMail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editMail.setError("Email is required");
            editMail.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        else if(password.length() < 6){
            editPassword.setError("Please enter password with at least 6 characters");
            editPassword.requestFocus();
            return;
        }
        mauth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,Home.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your Email to verify your account!",Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this,"Please enter valid credentials",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}