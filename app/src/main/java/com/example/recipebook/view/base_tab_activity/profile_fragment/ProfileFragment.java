package com.example.recipebook.view.base_tab_activity.profile_fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;
import com.example.recipebook.databinding.FragmentProfileBinding;
import com.example.recipebook.model.User;
import com.example.recipebook.service.UserService;
import com.example.recipebook.utils.ImageConverter;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileFragment extends Fragment {

    public static final int CAMERA_ACTION_RESULT = 1;
    private FragmentProfileBinding binding;

    private UserService userService;

    private Bitmap bitmap;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        
        setListener();

        setUI();
        return binding.getRoot();
    }

    private void setListener() {
        binding.btnUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_ACTION_RESULT);
            }
        });

        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validation())
                    return;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String authUser = preferences.getString("AuthUser", "");
                String password = preferences.getString("password", "");

                Gson gson = new Gson();

                User user = gson.fromJson(authUser, User.class);

                userService = new UserService(user.getEmail(), password);
                if(bitmap != null) {
                    user.setProfilePicture(
                            new ImageConverter().getStringByteFromBitmap(bitmap));
                }
                user.setEmail(binding.enterEmail.getText().toString());
                user.setPhoneNumber(binding.enterTextPhone.getText().toString());
                user.setNickname(binding.enterName.getText().toString());

                userService.putUser(user);
                Toast.makeText(getContext(), "Данные успешно изменн=енны", Toast.LENGTH_LONG).show();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setUI(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String authUser = preferences.getString("AuthUser", "");

        Gson gson = new Gson();

        User user = gson.fromJson(authUser, User.class);

        binding.enterName.setText(user.getNickname());
        binding.enterEmail.setText(user.getEmail());
        binding.enterTextPhone.setText(user.getPhoneNumber());

        if(user.getProfilePicture() != null) {

            byte[] bytesImage = Base64.getDecoder().decode(user.getProfilePicture());
            Bitmap bmp = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);

            binding.userImage.setImageBitmap(bmp);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_ACTION_RESULT && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto  = (Bitmap)bundle.get("data");
            this.bitmap = finalPhoto;
            binding.userImage.setImageBitmap(finalPhoto);
        }
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALIDE_PHONE_REGEX =
            Pattern.compile("((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}", Pattern.CASE_INSENSITIVE);


    private boolean validation() {
        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(binding.enterEmail.getText().toString());
        Matcher phoneMatcher = VALIDE_PHONE_REGEX.matcher(binding.enterTextPhone.getText().toString());

        if (binding.enterTextPhone.getText().toString().equals("")){
            Toast.makeText(getContext(), "Введите телефон", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterName.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Введите имя", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.enterEmail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Введите имя", Toast.LENGTH_LONG).show();
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