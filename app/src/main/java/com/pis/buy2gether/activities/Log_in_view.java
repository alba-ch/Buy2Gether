package com.pis.buy2gether.activities;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pis.buy2gether.MainActivity;
import com.pis.buy2gether.R;
import com.pis.buy2gether.SplashScreenActivity;

public class Log_in_view extends AppCompatActivity {
    private ImageButton log;
    private Button sign;
    private TextView name;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in_view);


        log = findViewById(R.id.btn_login);
        sign = findViewById(R.id.login);

        name = findViewById(R.id.img_appname);
        image = findViewById(R.id.img_logo);

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
            /*
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(image, "image");
            pairs[1] = new Pair<View, String>(name, "text");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Log_in_view.this, pairs);
            startActivity(i, options.toBundle());*/
            startActivity(i);
            finish();
        } );

    }
}