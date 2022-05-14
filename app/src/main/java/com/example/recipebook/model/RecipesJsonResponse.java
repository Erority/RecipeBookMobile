package com.example.recipebook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipesJsonResponse {
    @SerializedName("data")
    @Expose
    private List<Recipe> dataList = new ArrayList<>();

    public List<Recipe> getDataList() {
        return dataList;
    }
}
