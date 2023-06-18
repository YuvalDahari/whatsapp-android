package com.example.ourwhatsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.content.Context;


import java.io.ByteArrayOutputStream;

public class Utils {

    public static void displayBase64Image(String base64String, ImageView imageView) {
        // Decode the Base64 string to a byte array
        byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);

        // Convert the byte array to a Bitmap
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Display the image
        imageView.setImageBitmap(decodedImage);
    }

    public static String imageToBase64(Context context, int resourceId) {
        // Load the image from the drawable resources
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        // Convert the Bitmap to a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Convert the ByteArrayOutputStream to a byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convert the byte array to a base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
