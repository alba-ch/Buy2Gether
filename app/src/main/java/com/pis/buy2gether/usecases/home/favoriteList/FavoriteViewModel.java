package com.pis.buy2gether.usecases.home.favoriteList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pis.buy2gether.model.domain.data.FavoriteData;
import com.pis.buy2gether.model.domain.pojo.Favorite;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

import java.util.ArrayList;
import java.util.Collection;

public class FavoriteViewModel extends ViewModel {
    private int state;
    private MutableLiveData<ArrayList<Favorite>> favoriteList;

    public FavoriteViewModel() {
        favoriteList = new MutableLiveData<>();
        state = 1;
        init();
    }

    public FavoriteViewModel(int state){
        favoriteList = new MutableLiveData<>();
        this.state = state;
        init();
    }

    public MutableLiveData<ArrayList<Favorite>> getFavoriteList() {
        populateList();
        return favoriteList;
    }

    private void init() {
        populateList();
    }


    private void populateList(){
        MutableLiveData<Collection<Task<DocumentSnapshot>>> data = FavoriteData.INSTANCE.getFavs();
        data.observeForever( list ->{
            Tasks.whenAll(list).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ArrayList<Favorite> favAux = new ArrayList<>();
                    for (Task<DocumentSnapshot> t : list) {
                        DocumentSnapshot  documentSnapshot = t.getResult();
                        if (t.isSuccessful()) {
                            Favorite f = documentSnapshot.toObject(Favorite.class);
                            if (f != null && f.getProces() == state) {
                                favAux.add(f);
                            }
                        }
                    }
                    this.favoriteList.setValue(favAux);
                }
            });
        });
    }

    public void delete(String groupID) {
        FavoriteData.INSTANCE.deleteFav(groupID);
    }

    public Boolean checkSession(){
        return Session.INSTANCE.getProvider() != ProviderType.GUEST;
    }
}