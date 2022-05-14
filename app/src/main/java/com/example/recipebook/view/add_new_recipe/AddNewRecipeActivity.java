package com.example.recipebook.view.add_new_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recipebook.databinding.ActivityAddNewRecipeBinding;

public class AddNewRecipeActivity extends AppCompatActivity {

    private ActivityAddNewRecipeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewRecipeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}