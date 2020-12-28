package com.example.diet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private Button update,back;
    private TextView height,weight;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userid=getIntent().getStringExtra("Uid");
        height=findViewById(R.id.editht);
        weight=findViewById(R.id.editwt);
        update=findViewById(R.id.update);
        back=findViewById(R.id.back);
        FirebaseDatabase.getInstance().getReference("Users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile=snapshot.getValue(User.class);
                if(profile!=null){
                    height.setText(profile.height);
                    weight.setText(profile.weight);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:
                String ht=height.getText().toString();
                String wt=weight.getText().toString();
                if(ht.isEmpty()){
                    height.setError("Height is required");
                    return;
                }
                if(wt.isEmpty()){
                    weight.setError("Weight is required");
                    return;
                }
                FirebaseDatabase.getInstance().getReference("Users").child(userid).child("height").setValue(ht);
                FirebaseDatabase.getInstance().getReference("Users").child(userid).child("weight").setValue(wt);
                Toast.makeText(Profile.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                startActivity(new Intent(Profile.this,Home.class));
                break;
        }
    }
}