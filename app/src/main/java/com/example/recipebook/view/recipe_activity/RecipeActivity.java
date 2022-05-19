package com.example.recipebook.view.recipe_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.recipebook.R;
import com.example.recipebook.databinding.ActivityRecipeBinding;
import com.example.recipebook.interfaces.PutCallBack;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.service.UserService;
import com.example.recipebook.view.base_tab_activity.cookbook_fragment.CookbookFragment;
import com.example.recipebook.view.base_tab_activity.favourites_fragment.FavouritesFragment;
import com.example.recipebook.viewmodel.base_tab_viewmodel.CookbookViewModel;
import com.example.recipebook.viewmodel.base_tab_viewmodel.RecipeViewModel;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;
    private Recipe currentRecipe;
    private RecipeViewModel viewModel;

    private MutableLiveData<Recipe> delRecipe = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> favouritesRecipes = new MutableLiveData<List<Recipe>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);


        Intent myIntent = getIntent();
        String jsonRecipe = myIntent.getStringExtra("Recipe");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authUser = preferences.getString("AuthUser", "");
        String password = preferences.getString("password", "");

        Gson gson = new Gson();

        User user = gson.fromJson(authUser, User.class);
        Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);
        currentRecipe = recipe;

        viewModel.setRecipeService(new RecipeService(user.getEmail(), password));
        viewModel.setCurrentRecipe(currentRecipe);

        setObservers();

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeActivity.this);

                alertDialog.setTitle("Предупреждение");
                alertDialog.setMessage("Вы действительно хотите удалить данный рецепт ?");

                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteRecipe(currentRecipe.getId());
                    }
                });

                alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       AlertDialog  ad = alertDialog.create();
                       ad.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        binding.addOrDeleteFromFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getHasInFavourites().getValue()){
                    viewModel.deleteFromFavourites(currentRecipe.getId());
                    setResult(FavouritesFragment.CHANGE_FAVOURITE_RECIPE);
                } else {
                    viewModel.addToFavourites(currentRecipe.getId());
                    setResult(FavouritesFragment.CHANGE_FAVOURITE_RECIPE);
                }
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CookbookFragment.ADD_OR_DELETE_RECIPE_CODE);
                finish();
            }
        });

        setUI(recipe);
    }

    private void setObservers(){

        viewModel.getDelFavouriteRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                viewModel.getHasInFavourites().setValue(false);
            }
        });

        viewModel.getFavouritesRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

                for (Recipe recipe: recipes) {
                    if (recipe.getId() == currentRecipe.getId()){
                        viewModel.getHasInFavourites().setValue(true);
                        break;
                    }
                }
            }
        });

        viewModel.getDelRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                setResult(CookbookFragment.ADD_OR_DELETE_RECIPE_CODE);
                finish();
            }
        });

        viewModel.getHasInFavourites().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.addOrDeleteFromFavourites.setImageDrawable(getDrawable(R.drawable.fillheart));
                } else {
                    binding.addOrDeleteFromFavourites.setImageDrawable(getDrawable(R.drawable.heart));
                }
            }
        });
    }

    private void setUI(Recipe recipe) {
        byte[] bytesImage = Base64.getDecoder().decode(recipe.getImage());
        Bitmap bmp = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Bitmap bitmap = null;

        if(bmp != null)
            bitmap = Bitmap.createScaledBitmap(bmp, width, (int) (height * 0.4), false);

        StringBuilder sb = new StringBuilder();

        if (bitmap != null)
            binding.imageView.setImageBitmap(bitmap);

        binding.recipeName.setText(recipe.getTitle());
        binding.minutes.setText(sb.append(recipe.getCookingTimeMinutes()));
        sb = new StringBuilder();
        binding.portions.setText(sb.append(recipe.getPortions()));
        binding.recipeContent.setText(recipe.getContents());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
