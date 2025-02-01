package com.example.cookingapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String baseURL = "https://api.spoonacular.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder(); // Create a builder
            builder.baseUrl(baseURL); // Set base url
            builder.addConverterFactory(GsonConverterFactory.create()); // Parse data from json
            retrofit = builder.build(); // Creating retrofit object to make requests
        }
        return retrofit;
    }
}
