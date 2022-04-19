package com.pis.buy2gether.usecases.home.user.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.session.Session;

public class FriendsViewModel extends ViewModel {

    public Task getFriends(){
        return Session.INSTANCE.getFriendsDB(getUserID());
    }

    public String getUserID(){
        String userID = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return userID;
    }

    public Task<DocumentSnapshot> getUserName(String id) {
        return Session.INSTANCE.getUserByID(id);
    }
    /*public Task<DocumentSnapshot> getFriendshipID(String friendID) {
        return Session.INSTANCE.getFriendshipID(getUserID(), friendID);
    }*/
    public void deleteFriend(String id){
        Session.INSTANCE.deleteFriend(id);
    }
}