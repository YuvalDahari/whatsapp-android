package com.example.ourwhatsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.content.Context;


import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // Check if context is null
        if(context == null){
            return null;
        }

        // Load the image from the drawable resources
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        // Check if bitmap is null
        if(bitmap == null){
            return null;
        }

        // Convert the Bitmap to a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Convert the ByteArrayOutputStream to a byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convert the byte array to a base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String imagePathToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static boolean isValidDisplayName(String displayName) {
        String regex = "^[a-zA-Z0-9][a-zA-Z0-9 ]{4,}[a-zA-Z0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(displayName);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9\\s])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUsername(String username) {
        String regex = "^[A-Za-z0-9]{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
