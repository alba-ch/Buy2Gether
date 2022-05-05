package com.pis.buy2gether.model.domain.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.provider.services.FirebaseComanda;
import com.pis.buy2gether.provider.services.FirebaseFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public enum ComandasData{
    INSTANCE;

    private final FirebaseComanda firebaseComanda = (FirebaseComanda) FirebaseFactory.INSTANCE.getFirebase("FirebaseComanda");
    private MutableLiveData<ArrayList<String>> comandas = new MutableLiveData<>();
    private HashSet<String> comandes_id = new HashSet<>();
    private ArrayList<Grup> grups = new ArrayList<Grup>();


    public MutableLiveData<ArrayList<String>> getComandes() {
        updateComandes();
        return comandas;
    }



    public <grups> void updateComandes() {
        firebaseComanda.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                ArrayList<String> grups = (ArrayList<String>) document.getData().get("Grups");
                comandas.setValue(grups);
                comandas.postValue(grups);
            }
        });
    }

    public boolean isComanda(String grupID) {
        return comandes_id.contains(grupID);
    }

    public void saveComanda(String grupID) {
        comandes_id.add(grupID);
        firebaseComanda.saveComanda(grupID);
    }


    public void deleteComanda(String grupID) {
        firebaseComanda.deleteComanda(grupID);
    }

}

