package com.pis.buy2gether.usecases.home.favoriteList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

public class FavoriteViewModel extends ViewModel {


    public FavoriteViewModel() {
    }

    public Task<DocumentSnapshot> getFavorite() {
        return Session.INSTANCE.getFavorite(getUser());
    }

    public Task<DocumentSnapshot> getGroup(String id) {
        return Session.INSTANCE.getGroup(id);
    }


    public String getUser(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void delete(String id) {
        Session.INSTANCE.deleteFav(getUser(),id);
    }

    public Boolean checkSession(){
        return Session.INSTANCE.getProvider() != ProviderType.GUEST;
    }
}