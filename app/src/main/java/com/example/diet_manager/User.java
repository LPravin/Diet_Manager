package com.example.diet_manager;

public class User {
    public String name, email, password,height,weight;
    float bmi;

    public User(){

    }
    public User(String name, String email, String password, String height, String weight,float bmi){
        this.name=name;
        this.email=email;
        this.password=password;
        this.height=height;
        this.weight=weight;
        this.bmi=bmi;
    }
}

