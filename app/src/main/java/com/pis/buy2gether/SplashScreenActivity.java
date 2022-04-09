package com.pis.buy2gether;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pis.buy2gether.activities.Log_in_view;
import com.pis.buy2gether.activities.Product_view;

public class SplashScreenActivity extends AppCompatActivity {
    public static boolean login = false;
    private static int SPLASH_TIME_OUT = 1500;
    private Animation topAnim, bottomAnim;
    private ImageView image;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.shopping);
        name = findViewById(R.id.name);

        //Set animation
        image.setAnimation(bottomAnim);
        name.setAnimation(topAnim);

        new Handler().postDelayed(() -> {
            Intent i;
            if (login) {
                i = new Intent(SplashScreenActivity.this, MainActivity.class);
            }
            else {
                i = new Intent(SplashScreenActivity.this, Log_in_view.class);
            }
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }


}
