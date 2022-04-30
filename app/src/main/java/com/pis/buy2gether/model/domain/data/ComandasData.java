package com.pis.buy2gether.model.domain.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.provider.services.FirebaseComanda;
import com.pis.buy2gether.provider.services.FirebaseFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public enum ComandasData {
    INSTANCE;

    private final FirebaseComanda firebaseComanda = (FirebaseComanda) FirebaseFactory.INSTANCE.getFirebase("FirebaseComanda");
    private ArrayList<Grup> comandas = new ArrayList<>();
    private HashSet<String> comandes_id = new HashSet<>();



    public ArrayList<Grup> getComandes(int state) {
        updateComandes();
        if(state == -1){
            return new ArrayList<>(comandas);
        }
        ArrayList<Grup> grupList = new ArrayList<>();
        for(Grup grup : comandas){
            if(grup.getProces() == state){
                grupList.add(grup);
            }
        }
        return grupList;
    }


    public void setComandes(ArrayList<Grup> comandas) {
        this.comandas = comandas;
    }


    private void updateComandes() {
        firebaseComanda.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                List<String> group = (List<String>) document.getData().get("Grups");
                comandes_id = new HashSet<>(group);
                //Log.i("List Groups", group.toString());
                ArrayList<Grup> grups = new ArrayList<>();
                /*if(group != null){
                    for(String id : group){
                        Grup d = new Grup();
                        grups.add(d);
                    }
                }*/
                setComandes(grups);
            }
        });
    }

    public boolean isComanda(String grupID) {
        return comandes_id.contains(grupID);
    }

    public void saveComanda(String grupID) {
        firebaseComanda.saveComanda(grupID);
    }

    public void setComades(ArrayList<Grup> comandes){
        this.comandas = comandes;
    }

    public void deleteComanda(String grupID) {
        firebaseComanda.deleteComanda(grupID);
    }





}

