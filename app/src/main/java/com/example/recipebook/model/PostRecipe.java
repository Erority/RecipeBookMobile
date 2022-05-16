package com.example.recipebook.model;

import com.google.gson.annotations.SerializedName;

public class PostRecipe {
    @SerializedName("Title")
    final String title;
    @SerializedName("Contents")
    final String contents;
    @SerializedName("Portions")
    final int portions;
    @SerializedName("CookingTimeMinutes")
    final int cookingTimeMinutes;
    @SerializedName("Image")
    final String image;

    public PostRecipe(String title, String contents, int portions, int cookingTimeMinutes, String image){
        this.title = title;
        this.contents = contents;
        this.portions = portions;
        this.cookingTimeMinutes = cookingTimeMinutes;
        this.image = image;
    }
}
