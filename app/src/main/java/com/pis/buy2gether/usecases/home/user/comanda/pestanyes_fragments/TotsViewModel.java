package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.pis.buy2gether.model.domain.data.ComandasData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.session.Session;

import java.util.ArrayList;
import java.util.UUID;

public class TotsViewModel extends ViewModel{

    private int state;
    private MutableLiveData<ArrayList<Grup>> grupList;

    public TotsViewModel() {
        grupList = new MutableLiveData<>();
        state = -1;
        init();
    }

    public MutableLiveData<ArrayList<Grup>> getGrupList() {
        populateList();
        return grupList;
    }

    private void init() {
        populateList();
    }


    private void populateList(){
       test();
    }

    private void test() {
        Log.e("UUID", Session.INSTANCE.getCurrentUserID());
        Grup grup = new Grup();
        String uuid = UUID.randomUUID().toString();
        grup.setId(uuid);
        grup.setName("Grup de prova");
        grup.setDate("11-11-11");
        grup.setProces(1);
        grup.setPrice(10.65f);
        GrupData.INSTANCE.saveGrup(grup);
        ComandasData.INSTANCE.saveComanda(uuid);

        ArrayList<Grup> grupList = new ArrayList<>();
        grupList.add(grup);
        this.grupList.setValue(grupList);

    }



}