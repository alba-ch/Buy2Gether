package com.pis.buy2gether.usecases.home.user.address;

import android.text.Editable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.model.session.Session;

import java.util.HashMap;

public class AddressViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friends fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    void saveAddressDB(String nom, String address, String tel, String cp){
        HashMap addressInfo = new HashMap();
        addressInfo.put("Address name",nom);
        addressInfo.put("Full address",address);
        addressInfo.put("Telephone",tel);
        addressInfo.put("Zip code",cp);

        Session.INSTANCE.saveAddressDB(getUser(),addressInfo);
    }

    private String getUser(){
        String emailUser = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            emailUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        return emailUser;
    }

    void deleteAddressDB(String nom){
        Session.INSTANCE.deleteAddressDB(getUser(),nom);
    }

    public HashMap addressInfo(String nom, String address, String tel, String cp){
        HashMap addressInfo = new HashMap();
        addressInfo.put("Address name",nom);
        addressInfo.put("Full address",address);
        addressInfo.put("Telephone",tel);
        addressInfo.put("Zip code",cp);
        return addressInfo;
    }

    public Task getAddresses(){
        String emailUser = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            emailUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        return Session.INSTANCE.getAddressesDB(emailUser);
    }
}