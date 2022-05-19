package com.pis.buy2gether.model.domain.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.services.FirebaseAddress;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.usecases.home.user.address.AddressListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public enum AddressData{
    INSTANCE;

    private final FirebaseAddress firebaseAddress = (FirebaseAddress) FirebaseFactory.INSTANCE.getFirebase("FirebaseAddress");
    private MutableLiveData<ArrayList<Address>> data = new MutableLiveData<>();


    public MutableLiveData<ArrayList<Address>> getAddressList(String uid) {
        updateList(uid);
        return data;
    }

    public void setData(ArrayList<Address> data) {
        this.data.setValue(data);
    }

    public void updateList(String uid) {
        Task<QuerySnapshot> result = firebaseAddress.getAddresses(uid);

        result.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                ArrayList<Address> addresses = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : result.getResult()){
                    Address a = new Address();
                    Map<String, Object> info = documentSnapshot.getData();

                    a.setAddress((String) info.get("Full address"));
                    a.setName((String) info.get("Address name"));
                    a.setTelephone((String) info.get("Telephone"));
                    a.setZip((String) info.get("Zip code"));

                    System.out.println(a.getAddress());

                    addresses.add(a);
                }
                data.setValue(addresses);
                data.postValue(addresses);
            }
        });
    }

    public void saveAddress(String uid, String nom, String address, String tel, String cp){
        HashMap addressInfo = new HashMap();
        addressInfo.put("Address name",nom);
        addressInfo.put("Full address",address);
        addressInfo.put("Telephone",tel);
        addressInfo.put("Zip code",cp);
        firebaseAddress.saveAddress(uid,addressInfo);
        updateList(uid);
    }

    public void deleteAddress(String uid, String nom){
        firebaseAddress.deleteAddress(uid,nom);
        updateList(uid);
    }

}
