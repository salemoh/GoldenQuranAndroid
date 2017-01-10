package com.blackstone.goldenquran.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by SamerGigaByte on 11/12/2016.
 */

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "UserPrefernces";


    public static void putInteger(Context context, String key, int val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, val);
        // Commit the edits!
        editor.commit();

    }

    public static int getInteger(Context context, String key, int defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getInt(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, val);
        // Commit the edits!
        editor.commit();

    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getBoolean(key, defaultValue);
    }

    public static void putString(Context context, String key, String val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, val);
        // Commit the edits!
        editor.commit();

    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);

    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(key, defaultValue);

    }

    public void registerOnSharedPreferenceChangeListener(Context context,
                                                         SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        settings.registerOnSharedPreferenceChangeListener(listener);

    }

    public void unregisterOnSharedPreferenceChangeListener(Context context,
                                                           SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        settings.unregisterOnSharedPreferenceChangeListener(listener);

    }

    public static void putObject(Context context, String key, Object object) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        String jsonObject = gson.toJson(object);
        editor.putString(key, jsonObject);

        // Commit the edits!
        editor.commit();

    }

    public static <T> T getObject(Context context, String key, Class<T> cl) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        String json = settings.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(json, cl);

    }

}