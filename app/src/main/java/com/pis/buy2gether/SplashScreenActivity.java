package com.pis.buy2gether;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.pis.buy2gether.databinding.ActivityMainBinding;
import com.pis.buy2gether.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(r,3000);

    }

    final Runnable r = new Runnable() {
        public void run() {
            Intent startApp = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(startApp);
            finish();
        }
    };
}
