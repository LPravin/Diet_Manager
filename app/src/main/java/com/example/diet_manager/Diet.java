package com.example.diet_manager;

public class Diet {
    public String description,uses, risks, fte,fta;

    public Diet(){

    }
    public Diet(String description, String uses, String risks, String fte, String fta){
        this.description=description;
        this.uses=uses;
        this.risks=risks;
        this.fte=fte;
        this.fta=fta;
    }
}
