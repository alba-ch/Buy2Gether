package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.usecases.home.notifications.NotiType;

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

    public void sendInvite(String username,String toID, String fromID, String grupID) {
        HashMap data = new HashMap<>();
        data.put("FromUsername",username);
        data.put("fromID",fromID);
        data.put("groupname",grupID);
        data.put("toID",toID);
        data.put("notiType", NotiType.GROUP_INVITE);
        db.collection("Invites").document(UUID.randomUUID().toString()).set(data);
        db.collection("Requests").document(UUID.randomUUID().toString()).set(data);

    }
    /*
    * Task task = Session.INSTANCE.getUserByID(getUserID()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap inviteInfo = new HashMap();
                String username = documentSnapshot.get("username").toString();
                Log.e("FRIEND REQUEST","actual username is: " + username);
                inviteInfo.put("FromUsername",username);
                inviteInfo.put("fromID",getUserID());
                inviteInfo.put("groupname","");
                inviteInfo.put("toID",toID);
                inviteInfo.put("notiType", NotiType.FRIEND_REQUEST);
                Session.INSTANCE.CreateFriendRequest(inviteInfo);
            }
        });
    * */
}
