package com.example.diet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Profile extends AppCompatActivity {
    private Button update;
    private TextView height,weight;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        update=findViewById(R.id.update);
        height=findViewById(R.id.editht);
        weight=findViewById(R.id.editwt);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userid=user.getUid();
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile=snapshot.getValue(User.class);

                if(profile!=null){
                    String sht=profile.height;
                    String swt=profile.weight;
                    height.setText(sht);
                    weight.setText(swt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                reference.child(userid).child("height").setValue(ht);
                reference.child(userid).child("weight").setValue(wt);
                Toast.makeText(Profile.this,"Updated successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}