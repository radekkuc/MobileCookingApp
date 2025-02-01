package com.example.cookingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.StepViewHolder> {
    private List<RecipeDetails.Step> steps;

    public RecipeAdapter(List<RecipeDetails.Step> steps){
        this.steps = steps;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepDescription = itemView.findViewById(R.id.stepDescription);
        }
    }

    
    @NonNull
    @Override
    public RecipeAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.StepViewHolder holder, int position) {
        RecipeDetails.Step step = steps.get(position);
        String formattedStep = (position + 1) + ". " + step.getStep();
        holder.stepDescription.setText(formattedStep);
    }

    @Override
    public int getItemCount() {
        return (steps != null) ? steps.size() : 0;
    }

    public void updateSteps(List<RecipeDetails.Step> newSteps) {
        this.steps = newSteps;
        notifyDataSetChanged();
    }

}
