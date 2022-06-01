package com.pis.buy2gether.usecases.home.home.product_view;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.data.ComandasData;
import com.pis.buy2gether.model.domain.data.FavoriteData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

public class GrupViewModel {
    private MutableLiveData<Grup> lifeGrup;
    private Grup grup;

    public GrupViewModel(String grup) {
        this.lifeGrup = GrupData.INSTANCE.getGrup(grup);
    }

    public MutableLiveData<Grup> getLiveGrup(){
        return lifeGrup;
    }


    private Grup getGrup() {
        return grup;
    }
    public String getDescription() {
        return getGrup().getDescription();
    }

    public String getName() {
        return getGrup().getName();
    }


    public String getPrecio() {
        return String.valueOf(getGrup().getPrice()) + " €";
    }

    public MutableLiveData<Bitmap> getPhoto() {
        return ImageData.INSTANCE.getGrupPhoto(getGrup().getId());
    }

    public void addGrup(){
        ComandasData.INSTANCE.saveComanda(grup.getId());
    }
    public void setGrup(Grup g) {
        this.grup = g;
    }

    /**
     * add this group to favorite list
     */
    public void favGrup() {
        FavoriteData.INSTANCE.saveFav(grup.getId());
    }
}



