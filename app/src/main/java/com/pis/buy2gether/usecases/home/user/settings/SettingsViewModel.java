package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

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
     * ens guarda el nom de l'usuari a base de dades
     * @param nom
     */
    void saveNameUserDB(String nom){
        HashMap personalInfo = new HashMap();
        personalInfo.put("email",getEmail());
        personalInfo.put("username",nom);
        personalInfo.put("provider",getProvider());
        Session.INSTANCE.saveUserNameDB(getUser(),personalInfo);
    }

    /**
     * obtenim uid de l'usuari
     * @return
     */
    private String getUser(){
        String emailUser = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            emailUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return emailUser;
    }

    /**
     * obtenim el provider de l'usuari
     * @return userProvider
     */
    private String getProvider(){
        String userProvider = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            userProvider = FirebaseAuth.getInstance().getCurrentUser().getProviderId();
            ProviderType.BASIC.toString();
        }
        return userProvider;
    }

    /**
     * obtenim l'email de l'usuari
     * @return userEmail
     */
    private String getEmail(){
        String userEmail = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        return userEmail;
    }
}