package com.example.recipebook.view.recipe_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.recipebook.databinding.ActivityRecipeBinding;
import com.example.recipebook.model.Recipe;
import com.google.gson.Gson;

import java.util.Base64;

public class RecipeActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent myIntent = getIntent();
        String jsonRecipe = myIntent.getStringExtra("Recipe");

        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);

        setUI(recipe);
    }

    private void setUI(Recipe recipe){
        byte[] bytesImage = Base64.getDecoder().decode(recipe.getImage());
        Bitmap bmp = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, width, (int) (height * 0.4), false);

        StringBuilder sb = new StringBuilder();
        binding.imageView.setImageBitmap(bitmap);
        binding.recipeName.setText(recipe.getTitle());
        binding.minutes.setText(sb.append(recipe.getCookingTimeMinutes()));
        sb = new StringBuilder();
        binding.portions.setText(sb.append(recipe.getPortions()));
        binding.recipeContent.setText(recipe.getContents());
    }
}