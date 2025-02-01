package com.example.cookingapp;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private static final String apiKey = "Your api key";

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = view.findViewById(R.id.mainStepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String dishId = getArguments() != null ? getArguments().getString("recipeId") : "";
        fetchData(view, dishId);
        return view;
    }

    public void fetchData(View view, String dishId){
        SpoonacularApi api = ApiClient.getRetrofitInstance().create(SpoonacularApi.class);

        Call<RecipeInfo> infoCall = api.getInfo(dishId, apiKey);
        infoCall.enqueue(new Callback<RecipeInfo>() {
            @Override
            public void onResponse(Call<RecipeInfo> call, Response<RecipeInfo> response) {
                if(response.isSuccessful() && response.body() != null){
                    RecipeInfo info = response.body();

                    TextView title = view.findViewById(R.id.dishTitle);
                    title.setText(info.getTitle());

                    ImageView image = view.findViewById(R.id.dishImage);
                    Glide.with(view.getContext()).load(info.getImage()).into(image);
                }
                else{
                    Log.e("Error", "Couldnt load data, updateTitleImage");
                }
            }

            @Override
            public void onFailure(Call<RecipeInfo> call, Throwable t) {

            }
        });

        Call<List<RecipeDetails>> call = api.getRecipeDetails(dishId, apiKey);
        call.enqueue(new Callback<List<RecipeDetails>>() {
            @Override
            public void onResponse(Call<List<RecipeDetails>> call, Response<List<RecipeDetails>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<RecipeDetails> recipes = response.body();

                    if(!recipes.isEmpty()){
                        RecipeDetails mainRecipe = recipes.get(0);

                        displayMainRecipe(view, mainRecipe);

                        if(recipes.size() > 1){
                            TextView textView = view.findViewById(R.id.subRecipeTitle);
                            RecipeDetails subRecipe = recipes.get(1);
                            String subRecipeName = subRecipe.getName().isEmpty() ? "Sub-Recipe" : recipes.get(1).getName();

                            displaySubRecipe(view, subRecipe);
                            textView.setText(subRecipeName);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else{
                    Log.e("Error", "Couldnt load up data, fetchData");
                }
            }

            @Override
            public void onFailure(Call<List<RecipeDetails>> call, Throwable t) {
                Log.e("Error", "onFailure, fetch data");

            }
        });
    }


    public void displayMainRecipe(View view, RecipeDetails recipeDetails){
        Set<String> ingredientSet = new HashSet<>();
        TextView textView = view.findViewById(R.id.mainIngredients);
        for(RecipeDetails.Step step : recipeDetails.getSteps()){
            for(RecipeDetails.Ingredients ingredient : step.getIngredients()){
                ingredientSet.add(ingredient.getName());
            }
        }

        StringBuilder bulletList = new StringBuilder("<ul>");
        for(String ingredient : ingredientSet){
            bulletList.append("<li>").append(ingredient).append("</li>");
        }
        bulletList.append("</ul>");
        textView.setText(Html.fromHtml(bulletList.toString(), Html.FROM_HTML_MODE_COMPACT));

        RecyclerView recyclerView = view.findViewById(R.id.mainStepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        RecipeAdapter recipeAdapter = new RecipeAdapter(recipeDetails.getSteps());
        recyclerView.setAdapter(recipeAdapter);
    }

    public void displaySubRecipe(View view, RecipeDetails recipeDetails){
        Set<String> ingredientSet = new HashSet<>();
        TextView textView = view.findViewById(R.id.subRecipeIngredients);
        for(RecipeDetails.Step step : recipeDetails.getSteps()){
            for(RecipeDetails.Ingredients ingredient : step.getIngredients()){
                ingredientSet.add(ingredient.getName());
            }
        }

        StringBuilder bulletList = new StringBuilder("<ul>");
        for(String ingredient : ingredientSet){
            bulletList.append("<li>").append(ingredient).append("</li>");
        }
        bulletList.append("<ul>");
        textView.setText(Html.fromHtml(bulletList.toString(), Html.FROM_HTML_MODE_COMPACT));

        RecyclerView recyclerView = view.findViewById(R.id.subRecipeStepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipeDetails.getSteps());
        recyclerView.setAdapter(recipeAdapter);
    }
}
