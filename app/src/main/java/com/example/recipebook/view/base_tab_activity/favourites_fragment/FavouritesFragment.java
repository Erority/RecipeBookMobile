package com.example.recipebook.view.base_tab_activity.favourites_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipebook.R;
import com.example.recipebook.adapter.CookBookListAdapter;
import com.example.recipebook.databinding.FragmentCookbookBinding;
import com.example.recipebook.databinding.FragmentFavouritesBinding;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.viewmodel.base_tab_viewmodel.CookbookViewModel;
import com.example.recipebook.viewmodel.base_tab_viewmodel.FavouritesViewModel;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;
    private FavouritesViewModel viewModel;
    private CookBookListAdapter adapter;


    public FavouritesFragment() {
        // Required empty public constructor
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

        viewModel = new ViewModelProvider(requireActivity()).get(FavouritesViewModel.class);

        viewModel.getRecipesList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setItems(recipes);
            }
        });

        return binding.getRoot();
    }


    private void setAdapter(){
        adapter = new CookBookListAdapter();
        binding.recipesList.setAdapter(adapter);
    }

}