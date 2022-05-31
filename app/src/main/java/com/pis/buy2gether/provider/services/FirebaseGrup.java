package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.HashMap;
import java.util.UUID;

public class FirebaseGrup extends Firebase{

    public FirebaseGrup(){
        super();

    }

    public Task<DocumentSnapshot> getGrup(String grupId) {
        Task<DocumentSnapshot> documents = db.collection("Groups").document(grupId).get();
        return documents;
    }

    public void saveGrup(Grup grup) {
        db.collection("Groups").document(grup.getId()).set(grup);
    }

    public Task<QuerySnapshot> getGrup(){
        return db.collection("Groups").get();
    }

    public void sendInvite(String toUser, String fromUser, String grup) {
        HashMap<String,String> data = new HashMap<>();
        data.put("fromUser",fromUser);
        data.put("GrupID",grup);
        data.put("UserID",toUser);
        db.collection("Invites").document(UUID.randomUUID().toString()).set(data);
    }
}
