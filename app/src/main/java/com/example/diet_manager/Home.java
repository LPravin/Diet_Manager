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


public class Home extends AppCompatActivity implements View.OnClickListener {
    private TextView editBMI;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;
    private Button pm, prof,plan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editBMI=(TextView) findViewById(R.id.bmi);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        userid=user.getUid();
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile=snapshot.getValue(User.class);

                if(profile!=null){
                    String height=profile.height;
                    String weight=profile.weight;
                    float bmi=Float.parseFloat(weight)/((Float.parseFloat(height))*(Float.parseFloat(height))/10000);
                    editBMI.setText((String.format("%.2f",bmi)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });
        pm=findViewById(R.id.pmeter);
        prof=findViewById(R.id.profile);
        plan=findViewById(R.id.plan);
        pm.setOnClickListener(this);
        prof.setOnClickListener(this);
        plan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pmeter:
                startActivity(new Intent(Home.this, Pedometer.class));
                finish();
                break;
            case R.id.profile:
                Intent intent=new Intent(Home.this,Profile.class);
                intent.putExtra("Uid",userid);
                startActivity(intent);
                finish();
                break;
            case R.id.plan:
                startActivity(new Intent(Home.this,viewplans.class));
                break;
        }
    }
}