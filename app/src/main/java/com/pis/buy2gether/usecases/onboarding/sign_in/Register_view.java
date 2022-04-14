package com.pis.buy2gether.usecases.onboarding.sign_in;

import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.home.ProviderType;
import com.pis.buy2gether.usecases.onboarding.log_in.Log_in_view;

public class Register_view extends AppCompatActivity {
    private ImageButton sign;
    private Button log;
    private Button guest;
    private TextInputEditText userEditText;
    private TextInputEditText pswEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        sign = findViewById(R.id.btn_register);
        log = findViewById(R.id.login);
        guest = findViewById(R.id.invitado);

        userEditText = findViewById(R.id.txtin_username);
        pswEditText = findViewById(R.id.txtin_psw);


        sign.setOnClickListener(v -> {
            if(!(userEditText.getText().toString().isEmpty()) && !(pswEditText.getText().toString().isEmpty())){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEditText.getText().toString(),pswEditText.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                    }else{
                        showAlert();
                    }
                });
            }else{
                userEditText.startAnimation(shakeError());
                pswEditText.startAnimation(shakeError());
            }
        } );

        log.setOnClickListener(v -> {
            Intent i;
            i = new Intent(Register_view.this, Log_in_view.class);
            startActivity(i);
            finish();
        } );

        guest.setOnClickListener(v -> {
            showHome("guest", ProviderType.GUEST);
        });
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("S'ha produït un error autentificant l'usuari.");
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHome(String email, ProviderType provider){
        Intent i = new Intent(Register_view.this, MainActivity.class).putExtra("provider",provider.name()).putExtra("email",email);
        startActivity(i);
        finish();
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 0, 0, 20);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}