package com.example.cookingapp;

import java.util.List;

public class RecipeDetails {
    private final String name;
    private final List<Step> steps;

    public RecipeDetails(String name, List<Step> steps){
        this.name = name;
        this.steps = steps;
    }

    public String getName() {return name;}

    public List<Step> getSteps() {return steps;} // Returns every object in top layer array Steps

    public static class Step {
        private List<Ingredients> ingredients;
        private int number;
        private String step;
        private Length length;

        public Step(List<Ingredients> ingredients, String step, int number, Length length){
            this.ingredients = ingredients;
            this.step = step;
            this.number = number;
            this.length = length;
        }

        List<Ingredients> getIngredients() {return ingredients;}

        public int getNumber() {return number;}

        public String getStep() {return step;} // Return si

        public Length getLength() {return length;}
    }

    public static class Ingredients {
        private int id;
        private String name;

        public Ingredients(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {return id;}

        public String getName() {return name;}
    }

    public static class Length {
        private int number;
        private String unit;

        public Length(int number, String unit) {
            this.number = number;
            this.unit = unit;
        }

        public int getNumber() {return number;}

        public String getUnit() {return unit;}
    }
}
