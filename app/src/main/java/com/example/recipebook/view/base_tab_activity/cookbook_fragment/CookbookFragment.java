package com.example.recipebook.view.base_tab_activity.cookbook_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setListeners() {
        binding.buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNewRecipeActivity.class);
                startActivity(intent);
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

        return binding.getRoot();
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(recipe);
        intent.putExtra("Recipe", jsonObject);
        getContext().startActivity(intent);
    }
}