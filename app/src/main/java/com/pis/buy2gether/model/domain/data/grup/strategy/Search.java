package com.pis.buy2gether.model.domain.data.grup.strategy;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class Search {
    private MutableLiveData<ArrayList<Grup>> grupList;
    private StrategySearch st;

    public Search() {
    }

    public void setGrups(MutableLiveData<ArrayList<Grup>> grup) {
        this.grupList = grup;
    }

    public void setStrategy(StrategySearch strategy) {
        this.st = strategy;
    }

    public ArrayList<Grup> search() {
        return st.search(grupList);
    }

}
