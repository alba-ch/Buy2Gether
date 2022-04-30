package com.pis.buy2gether.model.domain.data.Grup.strategy;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class Search {
    private ArrayList<Grup> grup;
    private StrategySearch st;

    public Search() {
    }

    public void setGrups(ArrayList<Grup> grup) {
        this.grup = grup;
    }

    public void setStrategy(StrategySearch strategy) {
        this.st = strategy;
    }

    public ArrayList<Grup> search() {
        return st.search(grup);
    }

}
