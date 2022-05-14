package com.example.recipebook.view.base_tab_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.recipebook.R;
import com.example.recipebook.adapter.TabPageAdapter;
import com.example.recipebook.databinding.ActivityBaseTabBinding;
import com.google.android.material.tabs.TabLayout;

public class BaseTabActivity extends AppCompatActivity {
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
}