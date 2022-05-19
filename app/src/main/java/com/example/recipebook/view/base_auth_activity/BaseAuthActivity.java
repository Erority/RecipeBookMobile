package com.example.recipebook.view.base_auth_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipebook.R;
import com.example.recipebook.databinding.ActivityBaseAuthBinding;
import com.example.recipebook.interfaces.ISwitchFragment;
import com.example.recipebook.view.base_auth_activity.authorization.AuthFragment;
import com.example.recipebook.view.base_auth_activity.registration.RegFragment;
import com.example.recipebook.view.base_tab_activity.BaseTabActivity;

public class BaseAuthActivity extends AppCompatActivity implements ISwitchFragment {

    private ActivityBaseAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBaseAuthBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        switchFragment(1);
    }

     public void switchFragment(int fragment){
        Fragment currentFragment = null;

        switch (fragment){
            case 0:
                currentFragment = new RegFragment(this);
                binding.contentAuth.setText("Регистрация");
                break;
            case 1:
                currentFragment = new AuthFragment(this);
                binding.contentAuth.setText("Авторизация");
                break;
            case 2:
                Intent intent = new Intent(BaseAuthActivity.this, BaseTabActivity.class);
                startActivity(intent);
                break;
        }

        if (currentFragment != null) {
             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.auth_frame, currentFragment);
             transaction.commit();
         }
    }

    @Override
    public void switchWithParam(int option, String jsonObject) {

    }
}