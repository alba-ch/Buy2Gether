package com.pis.buy2gether.usecases.onboarding.log_in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

import java.util.HashMap;

class LoginViewModel extends ViewModel {
    private Context context;

    LoginViewModel(Context context){ this.context = context; }

    void showAlert(Task task){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("S'ha produït un error autentificant l'usuari.\n" + task.getException());
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 0, 0, 20);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }



    public MutableLiveData<String> googleLogIn(Intent data) {
        return Session.INSTANCE.googleLogIn(data);
    }

    public MutableLiveData<String> emailLogIn(String e, String p){
        return Session.INSTANCE.emailLogIn(e,p);
    }

    public void guestLogIn() {
        Session.INSTANCE.guestAccess();
    }
}
