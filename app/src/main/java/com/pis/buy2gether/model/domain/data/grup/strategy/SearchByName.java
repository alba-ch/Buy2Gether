package com.pis.buy2gether.model.domain.data.grup.strategy;

import android.os.Build;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchByName implements StrategySearch {

    @Override
    public ArrayList<Grup> search(ArrayList<Grup> grups) {
        ArrayList<Grup> ret_cat = new ArrayList<>();
        for (Grup grup : grups) {
            if(grup.getName() == Parameters.INSTANCE.filter){
                ret_cat.add(grup);
            }
        }
        return ret_cat;
    }
}
