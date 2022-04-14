package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.pis.buy2gether.model.session.Session;

public class SettingsViewModel extends ViewModel {

    private Context context;

    public SettingsViewModel(Context context) {
        this.context = context;
    }

    public void clearSession(){
        Session.INSTANCE.clearDataSession(context);
    }
}