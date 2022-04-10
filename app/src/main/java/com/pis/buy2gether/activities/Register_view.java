package com.pis.buy2gether.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.pis.buy2gether.MainActivity;
import com.pis.buy2gether.R;

public class Register_view extends AppCompatActivity {
    private Button sign;
    private Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        sign = findViewById(R.id.btn_register);
        log = findViewById(R.id.login);

        sign.setOnClickListener(v -> {
            // Check data
            Intent i;
            i = new Intent(Register_view.this, MainActivity.class);
            startActivity(i);
            finish();
        } );

        log.setOnClickListener(v -> {
            Intent i;
            i = new Intent(Register_view.this, Log_in_view.class);
            startActivity(i);
            finish();
        } );
    }
}