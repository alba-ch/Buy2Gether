package com.pis.buy2gether.ui.group.creation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupCreationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GroupCreationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}