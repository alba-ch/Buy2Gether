package com.pis.buy2gether.usecases.onboarding.sign_in;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.R;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.onboarding.log_in.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;

    private ImageButton sign;
    private Button log;
    private Button guest;
    private TextInputEditText userEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText pswEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        viewModel = new RegisterViewModel(this);

        setup();

        animateBackground();

        sign.setOnClickListener(v -> {
            String email = userEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String psw = pswEditText.getText().toString();

            /* Check if user filled both email and password text fields. */
            if(!(email.isEmpty()) && !(psw.isEmpty()) && !(username.isEmpty())){
                MutableLiveData<String> data = viewModel.emailSignIn(email, psw, username);
                data.observe(this, result->{
                    if(result.equals("Error 404")){
                        //Show error
                        viewModel.showAlert();
                    }else{
                        viewModel.showHome();
                    }
                });
            }else{
                userEditText.startAnimation(viewModel.shakeError());
                usernameEditText.startAnimation(viewModel.shakeError());
                pswEditText.startAnimation(viewModel.shakeError());
            }
        } );

        log.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } );

        guest.setOnClickListener(v -> {
            viewModel.showHome();
            viewModel.guestAccess();
            finish();
        });
    }

    private void setup(){
        sign = findViewById(R.id.btn_register);
        log = findViewById(R.id.login);
        guest = findViewById(R.id.invitado);

        usernameEditText = findViewById(R.id.txtin_username);
        userEditText = findViewById(R.id.txtin_email);
        pswEditText = findViewById(R.id.txtin_psw);
    }

    private void animateBackground(){
        ConstraintLayout cl = findViewById(R.id.register_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) cl.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}