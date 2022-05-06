package com.pis.buy2gether.model.domain.data.grup;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.data.grup.strategy.Parameters;
import com.pis.buy2gether.model.domain.data.grup.strategy.Search;
import com.pis.buy2gether.model.domain.data.grup.strategy.SearchByCategory;
import com.pis.buy2gether.model.domain.data.grup.strategy.SearchByName;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseGrup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public enum GrupData {
    INSTANCE;

    FirebaseGrup firebaseGrup = (FirebaseGrup) FirebaseFactory.INSTANCE.getFirebase("FirebaseGrup");
    private MutableLiveData<ArrayList<Grup>> grups = new MutableLiveData<>();
    private HashMap<String,Grup> hashgrups = new HashMap<>();
    private Search searcher = new Search();

    public void saveGrup(Grup grup) {
        firebaseGrup.saveGrup(grup);
    }

    public HashMap<String,Grup> getHashMap() {
        updateGrups();
        return hashgrups;
    }

    private void getGrup(String id, MutableLiveData<Grup> data_1) {
        firebaseGrup.getGrup(id).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.e("myTag", "Ejecutando");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("myTag", "Existe ops");
                        try{
                            Log.e("myTag", "L1");
                        Grup g = document.toObject(Grup.class);
                            Log.e("myTag", "L2" + g.getName());
                            data_1.setValue(g);
                            Log.e("myTag", "L3");
                            data_1.postValue(g);
                            Log.e("myTag", "Existe " + g.getId());

                        }
                        catch (Exception e){
                            Log.e("myTag", e.getMessage());
                        }

                    }
                }
            }
        });
    }

    public MutableLiveData<Grup> getGrup(String id) {
        MutableLiveData<Grup> data = new MutableLiveData<Grup>();
        getGrup(id, data);
        return data;
    }



    public MutableLiveData<ArrayList<Grup>> getAllGrups() {
        updateGrups();
        return grups;
    }

    public ArrayList<Grup> getGrupByCategory(Category category) {
        updateGrups();
        searcher.setGrups(grups);
        searcher.setStrategy(new SearchByCategory());
        Parameters.INSTANCE.category = category;
        return searcher.search();
    }

    public ArrayList<Grup> getGrupByName(String name) {
        updateGrups();
        searcher.setGrups(grups);
        searcher.setStrategy(new SearchByName());
        Parameters.INSTANCE.filter = name;
        return searcher.search();
    }

    private void updateGrups(){
        firebaseGrup.getGrup().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    ArrayList<Grup> data = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        Grup g = document.toObject(Grup.class);
                        data.add(g);
                    }
                    grups.setValue(data);
                    grups.postValue(data);
                }
            }
        });
    }




}
