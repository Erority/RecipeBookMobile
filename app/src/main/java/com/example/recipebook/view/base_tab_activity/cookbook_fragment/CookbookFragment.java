package com.example.recipebook.view.base_tab_activity.cookbook_fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;
import com.example.recipebook.adapter.CookBookListAdapter;
import com.example.recipebook.databinding.FragmentCookbookBinding;
import com.example.recipebook.interfaces.AdapterItemClickListener;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.utils.UpdateFavouriteRecipes;
import com.example.recipebook.view.add_new_recipe.AddNewRecipeActivity;
import com.example.recipebook.view.recipe_activity.RecipeActivity;
import com.example.recipebook.viewmodel.base_tab_viewmodel.CookbookViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CookbookFragment extends Fragment implements AdapterItemClickListener {

    private FragmentCookbookBinding binding;
    private CookbookViewModel viewModel;
    private CookBookListAdapter adapter;

    private UpdateFavouriteRecipes updateFavouriteRecipes;

    public CookbookFragment(UpdateFavouriteRecipes updateFavouriteRecipes){
        this.updateFavouriteRecipes = updateFavouriteRecipes;
    }

    public static final int ADD_OR_DELETE_RECIPE_CODE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setListeners() {
        binding.buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNewRecipeActivity.class);
                startActivityForResult(intent, ADD_OR_DELETE_RECIPE_CODE);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.filterCollection();
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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setAdapter(){
        adapter = new CookBookListAdapter(this);
        binding.recipesList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCookbookBinding.inflate(inflater, container, false);

        setListeners();

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recipesList.setLayoutManager(layoutManager);

        setAdapter();

        viewModel = new ViewModelProvider(requireActivity()).get(CookbookViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String authUser = preferences.getString("AuthUser", "");
        String password = preferences.getString("password", "");

        Gson gson = new Gson();

        User user = gson.fromJson(authUser, User.class);
        viewModel.setRecipeService(new RecipeService(user.getEmail(), password));

        setObservers();

        return binding.getRoot();
    }

    private void setObservers() {

        viewModel.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                viewModel.setAllListRecipe(recipes);
            }
        });

        viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
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
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(recipe);
        intent.putExtra("Recipe", jsonObject);
        startActivityForResult(intent, ADD_OR_DELETE_RECIPE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        viewModel.updateRecipes();
        updateFavouriteRecipes.isUpdate.setValue(true);
    }
}