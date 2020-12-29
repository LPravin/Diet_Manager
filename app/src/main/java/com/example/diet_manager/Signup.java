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
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private TextView editName,editMail,editPassword,editwt,editht;
    private Button submit;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        editName=findViewById(R.id.name);
        editMail=findViewById(R.id.mail);
        editPassword=findViewById(R.id.password);
        editwt=findViewById(R.id.wt);
        editht=findViewById(R.id.ht);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                registeruser();
                break;
        }

    }
    private void registeruser(){
        String email=editMail.getText().toString().trim();
        String name=editName.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String weight=editwt.getText().toString();
        String height=editht.getText().toString();

        if(name.isEmpty()){
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            editMail.setError("Email is required");
            editMail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editMail.setError("Please provide valid Email");
            editMail.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        else if(password.length()<6) {
            editPassword.setError("Please enter password with at least 6 characters");
            editPassword.requestFocus();
            return;
        }
        else if(height.isEmpty()){
            editht.setError("Height is required");
            editht.requestFocus();
            return;
        }
        else if(weight.isEmpty()){
            editwt.setError("Weight is required");
            editht.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(name,email,password,height,weight);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "Success!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Signup.this,MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(Signup.this,"Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(Signup.this,"Sorry... Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}