package com.example.recipebook.viewmodel.base_tab_viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.service.UserService;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Bitmap> profilePicture;

    private UserService userService;

    public ProfileViewModel(){

        userService = new UserService();
    }
}
