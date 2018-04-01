package com.mirath.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Anas Masri on 4/1/2018.
 */

public class SharedPrefUtils {

    private static final String LANGUAGE_SYMBOL = "lang";

    public static void saveLanguage(String languageSymbol) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppClass.getInstance());
        sharedPreferences.edit().putString(LANGUAGE_SYMBOL, languageSymbol).apply();

    }

    public static String getLanguage() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppClass.getInstance());
        return sharedPreferences.getString(LANGUAGE_SYMBOL, "en");

    }
}
