package com.example.recipebook.view.base_auth_activity.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;
import com.example.recipebook.databinding.FragmentRegBinding;
import com.example.recipebook.interfaces.ISwitchFragment;
import com.example.recipebook.model.PostUser;
import com.example.recipebook.model.User;
import com.example.recipebook.service.UserCall;
import com.example.recipebook.service.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegFragment extends Fragment {

    private FragmentRegBinding binding;
    private ISwitchFragment iSwitchFragment;

    private UserService userService;

    public RegFragment(ISwitchFragment iSwitchFragment) {
        this.iSwitchFragment = iSwitchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegBinding.inflate(inflater, container, false);

        userService = new UserService();

        setListeners();

        return binding.getRoot();
    }

    private void setListeners(){
        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSwitchFragment.switchFragment(1);
            }
        });

        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.postUser(
                        binding.enterName.getText().toString(),
                        binding.enterPassword.getText().toString(),
                        binding.enterEmail.getText().toString(),
                        binding.enterTextPhone.getText().toString());
            }
        });
    }
}