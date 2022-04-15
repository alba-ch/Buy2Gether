package com.pis.buy2gether.model.session;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.provider.preferences.PreferencesProvider;
import com.pis.buy2gether.provider.services.FirebaseRDBService;

import java.util.HashMap;

/* Class for user data persistence and write/read database info (only user session data)*/
public enum Session {
    INSTANCE;

    private FirebaseRDBService RDB = FirebaseRDBService.INSTANCE;

    public String getDataSession(Context context, String key){
        return PreferencesProvider.string(context, key);
    }

    public void setDataSession(Context context, String key, String value){
        PreferencesProvider.set(context, key, value);
    }

    public void clearDataSession(Context context){
        PreferencesProvider.clear(context);
    }

    public void saveDB(String coll, String doc, HashMap data){
        RDB.save(coll,doc,data);
    }

    public void deleteDB(String coll, String doc){
        RDB.delete(coll,doc);
    }

    public Task<DocumentSnapshot> getDB(String coll, String doc){
        return RDB.get(coll,doc);
    }

}
