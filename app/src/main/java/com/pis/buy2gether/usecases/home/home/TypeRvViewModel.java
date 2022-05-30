package com.pis.buy2gether.usecases.home.home;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class TypeRvViewModel {

    private MutableLiveData<ArrayList<Grup>> grupList;

    public TypeRvViewModel() {
        grupList = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Grup>> getGrupByCategory(Category category) {
        MutableLiveData<ArrayList<Grup>> grupList = GrupData.INSTANCE.getAllGrups();
        grupList.observeForever(grups -> {
            ArrayList<Grup> grupListFiltered = new ArrayList<>();
            for (Grup grup : grups) {
                if (grup.getCat() == category) {
                    grupListFiltered.add(grup);
                }
            }
            setList(grupListFiltered);
        });
        return this.grupList;
    }

    public static MutableLiveData<Bitmap> getPhoto(String grupID){
        return ImageData.INSTANCE.getGrupPhoto(grupID);
    }

    public MutableLiveData<ArrayList<Grup>> getGrupBySearch(String search) {
        MutableLiveData<ArrayList<Grup>> grupList = GrupData.INSTANCE.getAllGrups();
        grupList.observeForever(grups -> {
            ArrayList<Grup> grupListFiltered = new ArrayList<>();
            for (Grup grup : grups) {
                if (grup.getName().toLowerCase().contains(search.toLowerCase())) {
                    grupListFiltered.add(grup);
                }
            }
            setList(grupListFiltered);
        });
        return this.grupList;
    }

    private void setList(ArrayList<Grup> grups) {
        grupList.setValue(grups);
    }
}
