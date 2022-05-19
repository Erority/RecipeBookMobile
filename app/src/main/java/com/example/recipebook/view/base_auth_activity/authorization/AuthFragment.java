package com.example.recipebook.view.base_auth_activity.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;
import com.example.recipebook.databinding.FragmentAuthBinding;
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

    private MutableLiveData<String> validationError = new MutableLiveData<>();

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
                if (!validation())
                    return;

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

            userService.getErrorString().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                }
            });
    }

    private boolean validation() {
        if (binding.enterTextPhone.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Заполните почту или номер телефона", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterPassword.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Заполните пароль", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
