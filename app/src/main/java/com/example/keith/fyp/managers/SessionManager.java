package com.example.keith.fyp.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.activities.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Sutrisno on 1/11/2015.
 */
public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "AndroidHivePref";
    public static final String KEY_IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USER_NAME = "UserName";
    public static final String KEY_USER_EMAIL = "UserEmail";
    public static final String KEY_USER_ROLE = "UserRole";
    public static final String KEY_USER_PHOTO = "UserPhoto";

    public static final String USER_CAREGIVER = "USER_CAREGIVER";
    public static final String USER_SUPERVISOR = "USER_SUPERVISOR";
    public static final String USER_DOCTOR = "USER_DOCTOR";

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email, String role, Bitmap photo){
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_ROLE, role);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String photoStr = Base64.encodeToString(b, Base64.DEFAULT);
        editor.putString(KEY_USER_PHOTO, photoStr);

        editor.commit();
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.remove(KEY_IS_LOGIN);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_PHOTO);
        editor.commit();

        DataHolder.resetNotificationGroupList();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        user.put(KEY_USER_EMAIL, pref.getString(KEY_USER_EMAIL, null));
        user.put(KEY_USER_ROLE, pref.getString(KEY_USER_ROLE, null));
        user.put(KEY_USER_PHOTO, pref.getString(KEY_USER_PHOTO, null));

        return user;
    }

    public boolean isUserSupervisor() {
        String role = pref.getString(KEY_USER_ROLE, "");
        if(role.equals(USER_SUPERVISOR)) {
            return true;
        }
        return false;
    }
}
