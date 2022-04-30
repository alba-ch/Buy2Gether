package com.pis.buy2gether.provider.services;

import java.util.HashMap;

public enum FirebaseFactory {
    INSTANCE;

    private HashMap<String, Firebase> firebaseMap = new HashMap<String, Firebase>();

    public Firebase getFirebase(String firebaseType) {
        if (this.firebaseMap.containsKey(firebaseType)) {
            return this.firebaseMap.get(firebaseType);
        } else {
            try {
                String name = Firebase.class.getPackage().getName();
                Firebase firebase = (Firebase) Class.forName(name+"."+firebaseType).newInstance();
                firebaseMap.put(firebaseType,firebase);
                return firebase;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
