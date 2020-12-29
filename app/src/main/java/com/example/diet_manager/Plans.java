package com.example.diet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Plans extends AppCompatActivity {
   private TextView title,description,uses,risks,fte,fta;
   private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        type=getIntent().getStringExtra("Type");
        title=findViewById(R.id.title);
        title.setText(type);
        description=findViewById(R.id.description);
        uses=findViewById(R.id.uses);
        risks=findViewById(R.id.risks);
        fte=findViewById(R.id.fte);
        fta=findViewById(R.id.fta);
        FirebaseDatabase.getInstance().getReference("plans").child(type).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Diet d=snapshot.getValue(Diet.class);
                if(d!=null){
                    description.setText(d.description);
                    uses.setText(d.uses);
                    risks.setText(d.risks);
                    fte.setText(d.fte);
                    fta.setText(d.fta);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Plans.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}