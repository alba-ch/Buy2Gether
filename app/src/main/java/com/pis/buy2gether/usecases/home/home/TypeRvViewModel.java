package com.pis.buy2gether.usecases.home.home;

import android.graphics.Bitmap;

import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class TypeRvViewModel {

    public static ArrayList<Grup> getGrupByCategory(Category category) {
        return GrupData.INSTANCE.getGrupByCategory(category);
    }

    public static Bitmap getPhoto(String grupID){
        return ImageData.INSTANCE.getGrupPhoto(grupID);
    }

    public static ArrayList<Grup> getGrupBySearch(String search) {
        return GrupData.INSTANCE.getGrupByName(search);
    }
}
