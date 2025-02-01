package com.example.cookingapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    private List<Dish> dishes;
    private OnDishClickListener listener;

    DishAdapter(List<Dish> dishes, OnDishClickListener listener) {
        this.dishes = dishes;
        this.listener = listener;
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder{
        ImageView dishImage;
        TextView dishTitle;

        DishViewHolder(@NonNull View itemView){
            super(itemView);
            dishImage = itemView.findViewById(R.id.dishImage);
            dishTitle = itemView.findViewById(R.id.dishTitle);
        }
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get a LayoutInflater for the current context (parent is the RecyclerView here)
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the item_dish.xml layout and create a View object from it
        View view = inflater.inflate(R.layout.item_dish, parent, false);

        // The `view` now represents a single list item (but it's not attached to the RecyclerView yet)
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.dishTitle.setText(dish.getTitle());
        Glide.with(holder.itemView.getContext()).load(dish.getImage()).into(holder.dishImage);

        // Single item of recycler view click
        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onDishClick(dish);
            }
        });
    }

    public void updateDishes(List<Dish> newDishes){
        dishes = newDishes;
        notifyDataSetChanged();
    }

    public int getItemCount(){
        return (dishes != null) ? dishes.size() : 0;
    }

    // You can declare only one method if we want to use lambda expression, otherwise we have to define both in first fragment
    public interface OnDishClickListener {
        void onDishClick(Dish dish); // Declaration only, we define it ourselves in first fragment
    }

}
