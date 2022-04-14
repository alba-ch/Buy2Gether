package com.pis.buy2gether.model.session;

import android.content.Context;
import android.content.SharedPreferences;
import com.pis.buy2gether.R;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.provider.preferences.PreferencesProvider;

/* Class for user data persistence and write/read database info (only user session data)*/
public enum Session {
    INSTANCE;

    public String getDataSession(Context context, String key){
        return PreferencesProvider.string(context, key);
    }

    public void setDataSession(Context context, String key, String value){
        PreferencesProvider.set(context, key, value);
    }

    public void clearDataSession(Context context){
        PreferencesProvider.clear(context);
    }

}
