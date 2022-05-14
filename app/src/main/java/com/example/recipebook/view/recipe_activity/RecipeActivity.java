package com.example.recipebook.view.recipe_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recipebook.databinding.ActivityRecipeBinding;

public class RecipeActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}