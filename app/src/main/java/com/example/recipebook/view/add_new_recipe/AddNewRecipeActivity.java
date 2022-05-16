package com.example.recipebook.view.add_new_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.recipebook.databinding.ActivityAddNewRecipeBinding;
import com.example.recipebook.service.RecipeService;

public class AddNewRecipeActivity extends AppCompatActivity {

    RecipeService recipeService;
    private ActivityAddNewRecipeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recipeService = new RecipeService();
        binding = ActivityAddNewRecipeBinding.inflate(getLayoutInflater());

        binding.btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = Integer.valueOf(binding.enterMinutes.getText().toString());
                int portions = Integer.valueOf(binding.enterPortions.getText().toString());
                recipeService.postRecipe(binding.enterRecipeName.getText().toString(),
                        minutes, portions, binding.enterRecipeContent.getText().toString(), "");
            }
        });

        setContentView(binding.getRoot());
    }
}