package com.example.cookingapp;

public class RecipeInfo {
    private String title;
    private String image;

    RecipeInfo(String title, String image){
        this.title = title;
        this.image = image;
    }

    public String getTitle() {return title;}

    public String getImage() {return image;}
}
