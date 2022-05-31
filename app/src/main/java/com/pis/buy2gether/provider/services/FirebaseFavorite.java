package com.pis.buy2gether.provider.services;

import com.google.firebase.firestore.FieldValue;
import com.pis.buy2gether.model.session.Session;

public class FirebaseFavorite extends Firebase{

    public FirebaseFavorite(){
        super();
    }


    public void saveFav(String grup) {
        db.collection("users").document(Session.INSTANCE.getCurrentUserID()).update("Favorite", FieldValue.arrayUnion(grup));
    }

    public void deleteFav(String grup) {
        db.collection("users").document(Session.INSTANCE.getCurrentUserID()).update("Favorite", FieldValue.arrayRemove(grup));
    }


}
