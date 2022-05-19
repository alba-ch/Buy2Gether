package com.pis.buy2gether.provider.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.model.session.Session;

public class FirebaseNotification extends Firebase{


    public FirebaseNotification() {
        super();
    }

    public Task<QuerySnapshot> getFriendRequests() {
        Query q = db.collection("Requests").whereEqualTo("toID",(Session.INSTANCE.getCurrentUserID()));
        return q.get();
    }

    public Task<QuerySnapshot> getGroupInvites() {
        Query q = db.collection("Invites").whereEqualTo("UserID",(Session.INSTANCE.getCurrentUserID()));
        return q.get();
    }

    public Task<Void> deleteNotification(String id){
        Task<Void> delete = db.collection("Request").document(id).delete();
        return delete;
    }

    public void saveFriendRequest(Notificacions notificacions) {
        db.collection("Requests").document(notificacions.getIdNotificacion()).set(notificacions);
    }
}
