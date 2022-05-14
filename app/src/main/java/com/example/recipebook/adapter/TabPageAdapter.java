package com.example.recipebook.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.recipebook.view.base_auth_activity.authorization.AuthFragment;
import com.example.recipebook.view.base_auth_activity.registration.RegFragment;
import com.example.recipebook.view.base_tab_activity.cookbook_fragment.CookbookFragment;
import com.example.recipebook.view.base_tab_activity.favourites_fragment.FavouritesFragment;
import com.example.recipebook.view.base_tab_activity.profile_fragment.ProfileFragment;

public class TabPageAdapter extends FragmentStateAdapter {


    public TabPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CookbookFragment();
            case 1:
                return new FavouritesFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new CookbookFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

