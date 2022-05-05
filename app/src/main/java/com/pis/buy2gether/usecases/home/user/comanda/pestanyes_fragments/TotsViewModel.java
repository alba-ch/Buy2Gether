package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pis.buy2gether.model.domain.data.ComandasData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.session.Session;

import java.util.ArrayList;
import java.util.UUID;

public class TotsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Grup>> grupLiveData;
    private int state;
    private ArrayList<Grup> grupList;

    public TotsViewModel() {
        state = -1;
        grupLiveData = new MutableLiveData<>();
        init();
    }

    public ArrayList<Grup> getGrupList() {
        return grupList;
    }

    public MutableLiveData<ArrayList<Grup>> getUserMutableLiveData(){
        return grupLiveData;
    }
    private void init() {
        populateList();
        grupLiveData.setValue(grupList);
    }

    private void populateList(){
        grupList = new ArrayList<>();
        test();
    }

    private void test() {
        Log.e("UUID", Session.INSTANCE.getCurrentUserID());
        ComandasData.INSTANCE.getComandes(-1);
        Grup grup = new Grup();
        String uuid = UUID.randomUUID().toString();
        grup.setId(uuid);
        grup.setName("Grup de prova");
        grup.setDate("11-11-11");
        grup.setProces(1);
        grup.setPrice(10.65f);
        GrupData.INSTANCE.saveGrup(grup);
        ComandasData.INSTANCE.saveComanda(uuid);

        GrupData.INSTANCE.getGrup(uuid);

        if (GrupData.INSTANCE.getGrup(uuid) == null) {
            Log.e("Devolucion", uuid + "null pointer");
            Log.e("Grup", "No existeix");
        }else{
            Log.e("Grup SDSDS", grup.getId());
        }

        grup = new Grup();
        uuid = UUID.randomUUID().toString();
        grup.setId(UUID.randomUUID().toString());
        grup.setName("Grup de prova_1");
        grup.setDate("11-11-11");
        grup.setProces(0);
        grup.setPrice(10.65f);
        GrupData.INSTANCE.saveGrup(grup);
        ComandasData.INSTANCE.saveComanda(uuid);

        grup = new Grup();
        uuid = UUID.randomUUID().toString();
        grup.setId(UUID.randomUUID().toString());
        grup.setName("Grup de prova_2");
        grup.setDate("11-11-11");
        grup.setProces(1);
        grup.setPrice(10.65f);
        GrupData.INSTANCE.saveGrup(grup);
        ComandasData.INSTANCE.saveComanda(uuid);


    }


}