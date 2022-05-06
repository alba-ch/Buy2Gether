package com.pis.buy2gether.model.domain.data.grup.strategy;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchByName implements StrategySearch {

    @Override
    public ArrayList<Grup> search(MutableLiveData<ArrayList<Grup>> grups) {
        ArrayList<Grup> ret_cat = new ArrayList<>();

        return ret_cat;
    }
}
