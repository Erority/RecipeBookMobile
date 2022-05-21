package com.example.recipebook.view.base_auth_activity.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setObservers();

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

    private void setObservers() {
        userService.getAuthUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Toast.makeText(getContext(), "Регистарция прошла успешно", Toast.LENGTH_LONG).show();
                    iSwitchFragment.switchFragment(1);
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


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALIDE_PHONE_REGEX =
            Pattern.compile("((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}", Pattern.CASE_INSENSITIVE);

    private boolean validation() {

        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(binding.enterEmail.getText().toString());
        Matcher phoneMatcher = VALIDE_PHONE_REGEX.matcher(binding.enterTextPhone.getText().toString());

        if(binding.enterName.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Введите имя", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterPassword.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterEmail.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Введите почту", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterTextPhone.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Введите номер телефона", Toast.LENGTH_LONG).show();
            return false;
        } else if (emailMatcher.find() == false) {
            Toast.makeText(getContext(), "Почта неверного формата", Toast.LENGTH_LONG).show();
            return false;
        } else if (phoneMatcher.find() == false){
            System.out.println(phoneMatcher.find());
            Toast.makeText(getContext(), "Телефон неверного формата", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}