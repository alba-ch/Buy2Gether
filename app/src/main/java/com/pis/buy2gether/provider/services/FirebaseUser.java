package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.data.UserData;

import java.util.HashMap;
import java.util.List;

public class FirebaseUser extends Firebase{

    private com.google.firebase.auth.FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Link with Firebase Authentication

    public FirebaseUser() { super(); }

    /* ----- Current User Data ----- */
    public String getCurrentUserID(){
        return currentUser.getUid();
    }

    public com.google.firebase.auth.FirebaseUser getCurrentUser(){
        return currentUser;
    }

    public Task<DocumentSnapshot> getUserInformation(String user){
        return db.collection("users").document(user).get();
    }

    /* ---- User Data ---- */

    public Task<DocumentSnapshot> getUserByID(String id){
        return db.collection("users").document(id).get();
    }

    public Task<QuerySnapshot> getUsers(Boolean empty) {
        if (!empty) {
            return db.collection("users").whereNotIn(FieldPath.documentId(), UserData.INSTANCE.getFriendIDs()).get();
        }
        return db.collection("users").get();
    }
    public Task<QuerySnapshot> getFriends(String userID){
        Query q = db.collection("Friendships").whereNotEqualTo(userID,null);
        return q.get();
    }

    public void deleteFriend(String user, String doc){
        db.collection("users").document(user).collection("Addresses").document(doc).delete();
    }
}
