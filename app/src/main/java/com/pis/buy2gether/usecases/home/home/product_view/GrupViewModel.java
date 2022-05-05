package com.pis.buy2gether.usecases.home.home.product_view;

import android.graphics.Bitmap;

import com.pis.buy2gether.model.domain.data.ComandasData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

public class GrupViewModel {
    private Grup grup;

    public GrupViewModel(String grup) {
        this.grup = GrupData.INSTANCE.getGrup(grup);
    }

    public String getDescription() {
        return grup.getDescription();
    }

    public String getName() {
        return grup.getName();
    }

    public void btn_action() {
        if (ComandasData.INSTANCE.isComanda(grup.getId())) {
            ComandasData.INSTANCE.deleteComanda(grup.getId());
        }else{
            ComandasData.INSTANCE.saveComanda(grup.getId());
        }
    }

    public String btn_text() {
        if (ComandasData.INSTANCE.isComanda(grup.getId())) {
            return "Abandonar grup";
        }else{
            return "Unirse-grup";
        }
    }


    public String getPrecio() {
        return String.valueOf(grup.getPrice()) + " €";
    }

    public Bitmap getPhoto() {
        return ImageData.INSTANCE.getGrupPhoto(grup.getId());
    }



}



