package com.pis.buy2gether.model.domain.data;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseFavorite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public enum FavoriteData {
    INSTANCE;

    private final FirebaseFavorite firebaseFavorite = (FirebaseFavorite) FirebaseFactory.INSTANCE.getFirebase("FirebaseFavorite");
    private MutableLiveData<Collection<Task<DocumentSnapshot>>> favorite = new MutableLiveData<>();
    private HashSet<String> prefe_id = new HashSet<>();


    public MutableLiveData<Collection<Task<DocumentSnapshot>>> getFavs() {
        updateFavs();
        return favorite;
    }



    public <favorites> void updateFavs() {
        firebaseFavorite.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Collection<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                DocumentSnapshot document = task.getResult();
                Log.i("effefe", String.valueOf(document.getData()) + Session.INSTANCE.getCurrentUserID());
                if (document.getData() == null){
                    favorite.setValue(tasks);
                    return;
                }
                ArrayList<String> grups = (ArrayList<String>) document.getData().get("Favorite");
                if (grups != null) {
                    for(String grup : grups){
                        tasks.add(GrupData.INSTANCE.getGrupTask(grup));
                    }
                }
                favorite.setValue(tasks);
            }
        });
    }

    public boolean isComanda(String grupID) {
        return prefe_id.contains(grupID);
    }

    public void saveFav(String grupID) {
        prefe_id.add(grupID);
        firebaseFavorite.saveFav(grupID);
    }


    public void deleteFav(String grupID) {
        firebaseFavorite.deleteFav(grupID);
    }

}

