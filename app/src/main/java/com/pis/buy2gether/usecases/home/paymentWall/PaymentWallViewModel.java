package com.pis.buy2gether.usecases.home.paymentWall;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentWallViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PaymentWallViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}