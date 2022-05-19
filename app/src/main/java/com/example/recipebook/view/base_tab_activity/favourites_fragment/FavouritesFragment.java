package com.example.recipebook.view.base_tab_activity.favourites_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recipebook.adapter.CookBookListAdapter;
import com.example.recipebook.databinding.FragmentFavouritesBinding;
import com.example.recipebook.interfaces.AdapterItemClickListener;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.utils.UpdateFavouriteRecipes;
import com.example.recipebook.view.recipe_activity.RecipeActivity;
import com.example.recipebook.viewmodel.base_tab_viewmodel.FavouritesViewModel;
import com.google.gson.Gson;

import java.util.List;

public class FavouritesFragment extends Fragment implements AdapterItemClickListener {

    private FragmentFavouritesBinding binding;
    private FavouritesViewModel viewModel;
    private CookBookListAdapter adapter;


    public static final int CHANGE_FAVOURITE_RECIPE = 4;

    private UpdateFavouriteRecipes updateFavouriteRecipes;

    public FavouritesFragment(UpdateFavouriteRecipes updateFavouriteRecipes) {
        this.updateFavouriteRecipes = updateFavouriteRecipes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);


        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recipesList.setLayoutManager(layoutManager);

        setAdapter();
        setListeners();

        viewModel = new ViewModelProvider(requireActivity()).get(FavouritesViewModel.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String authUser = preferences.getString("AuthUser", "");
        String password = preferences.getString("password", "");

        Gson gson = new Gson();

        User user = gson.fromJson(authUser, User.class);
        viewModel.setRecipeService(new RecipeService(user.getEmail(), password));


        viewModel.getRecipesList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.clearItems();
                adapter.setItems(recipes);
            }
        });

        viewModel.getToastString().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                viewModel.getSearchString().setValue("");
                binding.searchEditText.setText("");
            }
        });

        updateFavouriteRecipes.isUpdate.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                viewModel.updateFavouriteList();
            }
        });

        viewModel.updateFavouriteList();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.filterCollection();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getSearchString().setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setAdapter(){
        adapter = new CookBookListAdapter(this);
        binding.recipesList.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        viewModel.updateFavouriteList();
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(recipe);
        intent.putExtra("Recipe", jsonObject);
        startActivityForResult(intent, CHANGE_FAVOURITE_RECIPE);
    }
}