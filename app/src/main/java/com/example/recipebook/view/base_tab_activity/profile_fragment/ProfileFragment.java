package com.example.recipebook.view.base_tab_activity.profile_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipebook.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        SharedPreferences sh = getContext().getSharedPreferences("UserSaver", Context.MODE_PRIVATE);

        Toast.makeText(getContext(), sh.getString("User",""), Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
/*
if (recipe.getImage() != null) {
        byte[] bytesImage = Base64.getDecoder().decode(recipe.getImage());
        Bitmap bmp = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
        Bitmap resized = Bitmap.createScaledBitmap(bmp, 177, 140, true);

        imageView.setImageBitmap(resized);
        }*/
