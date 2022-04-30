package com.pis.buy2gether.provider.services;

import com.google.firebase.firestore.FieldValue;
import com.pis.buy2gether.model.session.Session;

public class FirebaseComanda extends Firebase{

    public FirebaseComanda(){
        super();
    }


    public void saveComanda(String grup) {
        db.collection("users").document(Session.INSTANCE.getCurrentUserID()).update("Grups", FieldValue.arrayUnion(grup));
    }

    public void deleteComanda(String grup) {
        db.collection("users").document(Session.INSTANCE.getCurrentUserID()).update("Grups", FieldValue.arrayRemove(grup));
    }


}
