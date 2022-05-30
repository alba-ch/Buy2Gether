package com.pis.buy2gether.usecases.onboarding.sign_in;

import android.content.Context;
import android.content.Intent;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.provider.ProviderType;

import java.util.HashMap;
import java.util.Map;

class RegisterViewModel extends ViewModel {

    private Context context;

    RegisterViewModel(Context context){ this.context = context; }

    void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("S'ha produït un error autentificant l'usuari.");
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showHome(){
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 0, 0, 20);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }



    public MutableLiveData<String> emailSignIn(String e, String p, String username){
        return Session.INSTANCE.emailSignIn(e,p, username);
    }
}
