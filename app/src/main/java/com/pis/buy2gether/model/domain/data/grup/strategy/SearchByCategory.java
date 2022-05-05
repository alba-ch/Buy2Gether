package com.pis.buy2gether.model.domain.data.grup.strategy;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class SearchByCategory implements StrategySearch {

    @Override
    public ArrayList<Grup> search(ArrayList<Grup> grups) {
        ArrayList<Grup> ret_cat = new ArrayList<>();
        for (Grup grup : grups) {
            if(grup.getCat() == Parameters.INSTANCE.category){
                ret_cat.add(grup);
            }
        }
        return ret_cat;
    }
}
