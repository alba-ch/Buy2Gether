package com.pis.buy2gether.model.domain.data.Grup.strategy;

import android.os.Build;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchByCategory implements StrategySearch {


    @Override
    public ArrayList<Grup> search(ArrayList<Grup> grups) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return grups.stream().filter(g -> g.getCat().equals(Parameters.INSTANCE.category)).collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }
}
