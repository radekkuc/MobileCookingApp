package com.example.cookingapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchView = findViewById(R.id.editTextInput);
        ImageButton submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            String ingredient = searchView.getText().toString().trim();
            loadFirstFragment(ingredient);
        });
    }

    void loadFirstFragment(String ingredient){
        FirstFragment firstFragment = new FirstFragment();

        Bundle args = new Bundle();
        args.putString("ingredient", ingredient);
        firstFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, firstFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
