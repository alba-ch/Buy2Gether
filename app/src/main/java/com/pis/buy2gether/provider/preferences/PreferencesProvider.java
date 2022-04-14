package com.pis.buy2gether.provider.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* Saves data locally */
public class PreferencesProvider {

    private static SharedPreferences prefs(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void set(Context context, String key, String value){
        prefs(context).edit().putString(key,value).apply();
    }

    public static String string(Context context, String key){
        return prefs(context).getString(key,null);
    }

    public static void clear(Context context){
        prefs(context).edit().clear().apply();
    }

}
