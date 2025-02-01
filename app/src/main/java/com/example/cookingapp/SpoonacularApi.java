package com.example.cookingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularApi{
    @GET("recipes/findByIngredients")
    Call<List<Dish>> searchRecipes (
            @Query("ingredients") String ingredients,  // It will assign our URL our string value to ingredients
            @Query("apiKey") String apiKey             // ingredients = your_value
    );

    @GET("recipes/{id}/analyzedInstructions")
    Call<List<RecipeDetails>> getRecipeDetails(
            @Path("id") String recipeId,                       // allows to dynamically insert ID into url
            @Query("apiKey") String apiKey
    );

    @GET("recipes/{id}/information")
    Call<RecipeInfo> getInfo (
            @Path("id") String recipeId,
            @Query("apiKey") String apiKey
    );
}
