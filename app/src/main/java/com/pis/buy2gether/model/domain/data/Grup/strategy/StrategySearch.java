package com.pis.buy2gether.model.domain.data.Grup.strategy;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public interface StrategySearch {
    public ArrayList<Grup> search(ArrayList<Grup> grups);
}
