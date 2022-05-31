package com.pis.buy2gether.usecases.home.user.comanda.comanda_view;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

public class ComandaViewModel {
    private String product_id;
    private Grup grup;

    public ComandaViewModel(String id){
        product_id = id;
    }
    public void setGrup(Grup g){
        this.grup = g;
    }

    public MutableLiveData<Grup> getGrup(){
        return GrupData.INSTANCE.getGrup(product_id);
    }

    public MutableLiveData<Bitmap> getProductPhoto(){
        return ImageData.INSTANCE.getGrupPhoto(product_id);
    }

    public String getProductName(){
        return grup.getName();
    }
    public String getProductPrice(){
        return String.valueOf(grup.getPrice());
    }

    public String getProcess(){
        String proces = "NONE";
        switch (grup.getProces()) {
            case 0:
                proces = "EN PROCÈS";
                break;
            case 1:
                proces = "FINALITZAT";
                break;
            case 2:
                proces = "VALORAT";
                break;
            default:
                proces = "DESCONEGUT";
                break;

        }
        return proces;
    }
}
