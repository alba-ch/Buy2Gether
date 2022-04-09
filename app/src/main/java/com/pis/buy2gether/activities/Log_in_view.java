package com.pis.buy2gether.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.pis.buy2gether.MainActivity;
import com.pis.buy2gether.R;
import com.pis.buy2gether.SplashScreenActivity;

public class Log_in_view extends AppCompatActivity {
    private Button log;
    private Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in_view);


        log = findViewById(R.id.btn_login);
        sign = findViewById(R.id.registrar);

        log.setOnClickListener(v -> {
            // Check data
            Intent i;
            i = new Intent(Log_in_view.this, MainActivity.class);
            startActivity(i);
            finish();
        } );

        sign.setOnClickListener(v -> {
            Intent i;
            i = new Intent(Log_in_view.this, Register_view.class);
            startActivity(i);
            finish();
        } );

    }
}