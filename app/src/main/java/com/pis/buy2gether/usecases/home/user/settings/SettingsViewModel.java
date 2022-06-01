package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class SettingsViewModel extends ViewModel {

    private Context context;

    public SettingsViewModel(Context context) {
        this.context = context;
    }

    public void clearSession(){
        Session.INSTANCE.clearDataSession(context);
    }

    /**
     * actualitzem la localització de l'usuari
     * @param ciutat
     */
    void change_UserCity(String ciutat){
        Session.INSTANCE.updateUser(getUser(),"city",ciutat);
    }

    /**
     * actualitzem el nom de l'usuari
     * @param nom
     */
    void change_Username(String nom){
        Session.INSTANCE.updateUser(getUser(),"username",nom);
    }


    /**
     * obtenim les dades de l'usuari
     * @return
     */
    Task<DocumentSnapshot> update_UserInformation(){
        return Session.INSTANCE.getUserInformation(getUser());
    }

    /**
     * obtenim uid de l'usuari
     * @return
     */
    private String getUser(){
        return Session.INSTANCE.getCurrentUserID();
    }

    public MutableLiveData<Bitmap> getProfilePhoto(){
        return ImageData.INSTANCE.getProfilePhoto();
    }

    public void saveImage(Bitmap bm){
        ImageData.INSTANCE.saveProfilePhoto(bm);
    }

}