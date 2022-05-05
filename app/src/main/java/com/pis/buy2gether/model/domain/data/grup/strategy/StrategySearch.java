package com.pis.buy2gether.model.domain.data.grup.strategy;

import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public interface StrategySearch {
    public ArrayList<Grup> search(MutableLiveData<ArrayList<Grup>> grups);
}
