package com.pis.buy2gether.model.domain.data.Grup;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.data.Grup.strategy.Parameters;
import com.pis.buy2gether.model.domain.data.Grup.strategy.Search;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseGrup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum GrupData {
    INSTANCE;

    FirebaseGrup firebaseGrup = (FirebaseGrup) FirebaseFactory.INSTANCE.getFirebase("FirebaseGrup");
    private Grup grup;
    private ArrayList<Grup> grups;
    private Search searcher = new Search();

    public void saveGrup(Grup grup) {
        firebaseGrup.saveGrup(grup);
    }

    public Grup getGrup(String id) {
        firebaseGrup.getGrup(id).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.e("myTag", "Ejecutando");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Grup g = document.toObject(Grup.class);
                        grup = g;
                    }
                }
            }
        });
        Log.e("myTag", "Grup: " + String.valueOf(grup == null));
        return grup;
    }

    public ArrayList<Grup> getAllGrups() {
        updateGrups();
        return grups;
    }

    public ArrayList<Grup> getGrupByCategory(Category category) {
        updateGrups();
        searcher.setGrups(grups);
        Parameters.INSTANCE.category = category;
        return searcher.search();
    }

    public ArrayList<Grup> getGrupByName(String name) {
        updateGrups();
        searcher.setGrups(grups);
        Parameters.INSTANCE.filter = name;
        return searcher.search();
    }

    private void updateGrups(){
        firebaseGrup.getGrup().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    grups = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        Grup g = document.toObject(Grup.class);
                        grups.add(g);
                    }
                }
            }
        });
    }


}
