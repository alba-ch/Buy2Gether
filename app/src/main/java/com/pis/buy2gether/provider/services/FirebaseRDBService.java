package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/* Write or read data from Firebase Database */
public enum FirebaseRDBService {
    INSTANCE;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void save(String collectionPath, String doc, HashMap data){
        db.collection(collectionPath).document(doc).set(data);
    }

    private void save(String collectionPath, String doc, String data){
        db.collection(collectionPath).document(doc).set(data);
    }

    public void saveAddress(String doc, HashMap data){
        db.collection("users").document(doc).collection("Addresses").document(data.get("Address name").toString()).set(data);
    }


    public void saveGroup(String doc, HashMap data){
        db.collection("Groups").document(doc).set(data);
    }

    public void delete(String collectionPath, String doc){
        db.collection(collectionPath).document(doc).delete();
    }

    public void deleteAddress(String user, String doc){
        db.collection("users").document(user).collection("Addresses").document(doc).delete();
    }

    public Task<DocumentSnapshot> get(String collectionPath, String doc){
        return db.collection(collectionPath).document(doc).get();
    }


    public Task<DocumentSnapshot> getUserByID(String id){
        return db.collection("users").document(id).get();
    }

    public Task<QuerySnapshot> getFriends(String userID){
        Query q = db.collection("Friendships").whereNotEqualTo(userID,null);
        return q.get();
    }
    public Task<QuerySnapshot> getAddresses(String doc){
        Query q = db.collection("users").document(doc).collection("Addresses");
        return q.get();
    }

    public void SaveInviteDB(String uuidString, HashMap inviteInfo) {
        save("Invites",uuidString,inviteInfo);
    }
}
