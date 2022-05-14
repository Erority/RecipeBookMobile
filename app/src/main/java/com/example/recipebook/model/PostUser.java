package com.example.recipebook.model;

import com.google.gson.annotations.SerializedName;

public class PostUser {
    @SerializedName("Nickname")
    final String nickName;
    @SerializedName("PhoneNumber")
    final String phoneNumber;
    @SerializedName("ProfilePicture")
    final byte[] profilePicture;

    public PostUser(String nickName, String phoneNumber, byte[] profilePicture) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }
}
