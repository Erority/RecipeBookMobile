package com.example.recipebook.view.base_auth_activity.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;
import com.example.recipebook.databinding.FragmentAuthBinding;
import com.example.recipebook.databinding.FragmentCookbookBinding;
import com.example.recipebook.interfaces.ISwitchFragment;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.UserCall;
import com.example.recipebook.service.UserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private ISwitchFragment iSwitchFragment;
    private UserService userService;


    public AuthFragment(ISwitchFragment iSwitchFragment) {
        this.iSwitchFragment = iSwitchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);

        setListeners();

        return binding.getRoot();
    }


    private void setListeners(){
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSwitchFragment.switchFragment(0);
            }
        });

        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService = new UserService(binding.enterTextPhone.getText().toString(), binding.enterPassword.getText().toString());
                userService.getUser();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("password", binding.enterPassword.getText().toString());
                editor.apply();
                setObserver();
            }
        });
    }

    private void setObserver(){
            userService.getAuthUser().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        Gson gson = new Gson();

                        String jsonStrUser = gson.toJson(user);

                        editor.putString("AuthUser", jsonStrUser);
                        editor.putString("", "");
                        editor.apply();


                        iSwitchFragment.switchFragment(2);
                    }

                }
            });
    }
}