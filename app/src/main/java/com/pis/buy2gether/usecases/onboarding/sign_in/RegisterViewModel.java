package com.pis.buy2gether.usecases.onboarding.sign_in;

import android.content.Context;
import android.content.Intent;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.usecases.home.ProviderType;

class RegisterViewModel extends ViewModel {

    void showAlert(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("S'ha produït un error autentificant l'usuari.");
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showHome(String email, ProviderType provider, Context context){
        Intent i = new Intent(context, MainActivity.class).putExtra("provider",provider.name()).putExtra("email",email);
        context.startActivity(i);
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 0, 0, 20);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
