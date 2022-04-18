package com.pis.buy2gether.usecases.home.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.model.session.Session;

import java.util.HashMap;

public class NotificationsViewModel extends ViewModel {

    public void addFriend(String id1,String id2){
        HashMap<String,String> friendShip = new HashMap<>();
        friendShip.put(id1,id1);
        friendShip.put(id2,id2);
        Session.INSTANCE.addFriendship(friendShip);
    }

    public void removeGroupInvite(String id){
        Session.INSTANCE.deleteGroupInvite(id);
    }

    public String getUser(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public void removeFriendRequest(String id){
        Session.INSTANCE.deleteFriendRequest(id);
    }

    public void joinGroup(String user, String extraID) {
        HashMap<String,String> friendShip = new HashMap<>();
        friendShip.put("UserID",user);
        friendShip.put("GroupID",extraID);
        Session.INSTANCE.joinGroup(friendShip);
    }
}