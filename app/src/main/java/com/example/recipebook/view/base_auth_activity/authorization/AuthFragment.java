package com.example.recipebook.view.base_auth_activity.authorization;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private ISwitchFragment iSwitchFragment;


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
                UserService userService = new UserService(binding.enterTextPhone.getText().toString(), binding.enterPassword.getText().toString());
                UserCall userCall = userService.getRetrofit().create(UserCall.class);
                userCall.getUser().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()){
                            iSwitchFragment.switchFragment(2);
                            return;
                        }
                        else if (response.code() == 401)
                            Toast.makeText(getContext(), "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}