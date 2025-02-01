package com.example.cookingapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private DishAdapter dishAdapter;
    private static final String apiKey = "Your api key";

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDishes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String ingredient = getArguments() != null ? getArguments().getString("ingredient") : "";
        fetchData(ingredient);
        return view;
    }

    public void fetchData(String ingredient){
        SpoonacularApi api = ApiClient.getRetrofitInstance().create(SpoonacularApi.class);

        Call<List<Dish>> call = api.searchRecipes(ingredient, apiKey);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Dish> dishes = response.body();
                    dishAdapter = new DishAdapter(dishes, dish -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("recipeId", dish.getId()); // Pass the dish ID to SecondFragment

                        // Navigate to SecondFragment
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, SecondFragment.class, bundle)
                                .addToBackStack(null)
                                .commit();
                    });
                    recyclerView.setAdapter(dishAdapter);
                }
                else{
                    Log.e("Error", "onResponse: Connected but problem with api" );
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                Log.e("Error", "onFailure: Something went wrong" );
            }
        });
    }
}
