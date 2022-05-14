package com.example.recipebook.view.startscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recipebook.R;
import com.example.recipebook.databinding.ActivityStartBinding;
import com.example.recipebook.view.base_auth_activity.BaseAuthActivity;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, BaseAuthActivity.class);
                startActivity(intent);
            }
        });
    }
}