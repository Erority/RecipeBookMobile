package com.example.recipebook.view.base_tab_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recipebook.R;
import com.example.recipebook.adapter.TabPageAdapter;
import com.example.recipebook.databinding.ActivityBaseTabBinding;
import com.example.recipebook.interfaces.ISwitchFragment;
import com.example.recipebook.view.recipe_activity.RecipeActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BaseTabActivity extends AppCompatActivity  implements ISwitchFragment {
    private ActivityBaseTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTabLayout();
    }


    private void setTabLayout(){
        TabPageAdapter adapter = new TabPageAdapter(this);

        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position==0){
                    tab.setIcon(getResources().getDrawable(R.drawable.grid));
                } else if(position == 1){
                    tab.setIcon(getResources().getDrawable(R.drawable.heart));
                } else {
                    tab.setIcon(getResources().getDrawable(R.drawable.user));
                }
            }
        }).attach();


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void switchFragment(int fragment) {

    }

    @Override
    public void switchWithParam(int option, String jsonObject) {
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra("Recipe", jsonObject);
            startActivity(intent);
    }

}