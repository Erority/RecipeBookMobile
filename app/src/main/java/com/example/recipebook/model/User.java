package com.example.recipebook.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("profilePicture")
    private String profilePicture;
}
