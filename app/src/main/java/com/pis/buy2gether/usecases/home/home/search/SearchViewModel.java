package com.pis.buy2gether.usecases.home.home.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public static ArrayList<Grup> getGrupByName(String name) {
        return GrupData.INSTANCE.getGrupByName(name);
    }

}