package com.pis.buy2gether.usecases.onboarding.sign_in;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    private TextInputEditText pswEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        viewModel = new RegisterViewModel(this);

        setup();

        sign.setOnClickListener(v -> {
            String email = userEditText.getText().toString();
            String psw = pswEditText.getText().toString();

            /* Check if user filled both email and password text fields. */
            if(!(email.isEmpty()) && !(psw.isEmpty())){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,psw).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        viewModel.showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                        finish();
                    }else{
                        viewModel.showAlert();
                    }
                });
            }else{
                userEditText.startAnimation(viewModel.shakeError());
                pswEditText.startAnimation(viewModel.shakeError());
            }
        } );

        log.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } );

        guest.setOnClickListener(v -> {
            viewModel.showHome("guest", ProviderType.GUEST);
            finish();
        });
    }

    private void setup(){
        sign = findViewById(R.id.btn_register);
        log = findViewById(R.id.login);
        guest = findViewById(R.id.invitado);

        userEditText = findViewById(R.id.txtin_username);
        pswEditText = findViewById(R.id.txtin_psw);
    }
}