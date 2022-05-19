package com.example.recipebook.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageConverter {

    public String getStringByteFromBitmap(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(array);
    }
}
