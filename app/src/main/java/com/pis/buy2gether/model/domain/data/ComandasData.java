package com.pis.buy2gether.model.domain.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.provider.services.FirebaseComanda;
import com.pis.buy2gether.provider.services.FirebaseFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public enum ComandasData{
    INSTANCE;

    private final FirebaseComanda firebaseComanda = (FirebaseComanda) FirebaseFactory.INSTANCE.getFirebase("FirebaseComanda");
    private MutableLiveData<Collection<Task<DocumentSnapshot>>> comandas = new MutableLiveData<>();
    private HashSet<String> comandes_id = new HashSet<>();


    public MutableLiveData<Collection<Task<DocumentSnapshot>>> getComandes() {
        updateComandes();
        return comandas;
    }



    public <grups> void updateComandes() {
        firebaseComanda.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                ArrayList<String> grups = (ArrayList<String>) document.getData().get("Grups");
                Collection<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                for(String grup : grups){
                    tasks.add(GrupData.INSTANCE.getGrupTask(grup));
                }
                comandas.setValue(tasks);
                comandas.postValue(tasks);
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

