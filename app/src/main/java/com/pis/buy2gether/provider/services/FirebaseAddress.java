package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.session.Session;

import java.util.HashMap;
import java.util.List;

public class FirebaseAddress extends Firebase{

    public FirebaseAddress(){
        super();
    }

    public Task<QuerySnapshot> getAddresses(String doc){
        Query q = db.collection("users").document(doc).collection("Addresses");
        return q.get();
    }

    public void saveAddress(String doc, HashMap data){
        db.collection("users").document(doc).collection("Addresses").document(data.get("Address name").toString()).set(data);
    }

    public void deleteAddress(String user, String doc){
        db.collection("users").document(user).collection("Addresses").document(doc).delete();
    }
}
