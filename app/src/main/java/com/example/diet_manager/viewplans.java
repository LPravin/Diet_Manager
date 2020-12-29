package com.example.diet_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class viewplans extends AppCompatActivity implements View.OnClickListener {
    private TextView atkins,zone,vegetarian,vegan,rawfood;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewplans);
        atkins=findViewById(R.id.atkins);
        zone=findViewById(R.id.zone);
        vegetarian=findViewById(R.id.vegetarian);
        vegan=findViewById(R.id.vegan);
        rawfood=findViewById(R.id.rawfood);
        atkins.setOnClickListener(this);
        zone.setOnClickListener(this);
        vegetarian.setOnClickListener(this);
        vegan.setOnClickListener(this);
        rawfood.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(viewplans.this,Plans.class);
        switch (v.getId()){
            case R.id.atkins:
                type="atkins";
                break;
            case R.id.zone:
                type="zone";
                break;
            case R.id.vegetarian:
                type="vegetarian";
                break;
            case R.id.vegan:
                type="vegan";
                break;
            case R.id.rawfood:
                type="rawfood";
                break;
        }
        intent.putExtra("Type",type);
        startActivity(intent);
    }
}