package com.example.recipebook.view.add_new_recipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.recipebook.databinding.ActivityAddNewRecipeBinding;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.utils.ImageConverter;
import com.example.recipebook.view.base_tab_activity.cookbook_fragment.CookbookFragment;
import com.google.gson.Gson;

public class AddNewRecipeActivity extends AppCompatActivity {

    RecipeService recipeService;
    private ActivityAddNewRecipeBinding binding;
    public static final int CAMERA_ACTION_RESULT = 1;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authUser = preferences.getString("AuthUser", "");
        String password = preferences.getString("password", "");

        Gson gson = new Gson();

        User user = gson.fromJson(authUser, User.class);
        recipeService = new RecipeService(user.getEmail(), password);
        binding = ActivityAddNewRecipeBinding.inflate(getLayoutInflater());

        setListeners();

        recipeService.getPostRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                Toast.makeText(AddNewRecipeActivity.this, "Рецепт добавлен", Toast.LENGTH_LONG).show();
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setResult(CookbookFragment.ADD_OR_DELETE_RECIPE_CODE);
                finish();
            }
        });

        setContentView(binding.getRoot());
    }

    private void setListeners() {

        binding.btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validation())
                    return;

                int minutes = Integer.valueOf(binding.enterMinutes.getText().toString());
                int portions = Integer.valueOf(binding.enterPortions.getText().toString());
                recipeService.postRecipe(binding.enterRecipeName.getText().toString(),
                        minutes, portions, binding.enterRecipeContent.getText().toString(), new ImageConverter().getStringByteFromBitmap(bitmap));

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_ACTION_RESULT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_ACTION_RESULT && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto  = (Bitmap)bundle.get("data");
            this.bitmap = finalPhoto;
            binding.imageRecipe.setImageBitmap(finalPhoto);
        }
    }

    private boolean validation() {
        if(binding.enterRecipeName.getText().toString().trim().equals("")){
            Toast.makeText(this, "Заполните название рецепта", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterMinutes.getText().toString().trim().equals("")){
            Toast.makeText(this, "Заполните кол-во минут", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterPortions.getText().toString().trim().equals("")){
            Toast.makeText(this, "Заполните кол-во порций", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterRecipeContent.getText().toString().trim().equals("")){
            Toast.makeText(this, "Заполните кол-во минут", Toast.LENGTH_LONG).show();
            return false;
        } else if(bitmap == null){
            Toast.makeText(this, "Сделайте фото", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}