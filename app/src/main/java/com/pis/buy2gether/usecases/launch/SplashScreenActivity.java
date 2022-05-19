package com.pis.buy2gether.usecases.launch;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;


import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.model.domain.data.ComandasData;
import com.pis.buy2gether.model.domain.data.NotificationData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.onboarding.log_in.LoginActivity;

import java.util.UUID;

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

        //Test
        test();
        new Handler().postDelayed(() -> {
            Intent i;
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                i = new Intent(SplashScreenActivity.this, MainActivity.class).putExtra("provider",Session.INSTANCE.getDataSession(this,"provider")).putExtra("email",Session.INSTANCE.getDataSession(this,"email"));
                startActivity(i);
                finish();
            }
            else {
                i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "image");
                pairs[1] = new Pair<View, String>(name, "text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);
                startActivity(i, options.toBundle());
            }
        }, SPLASH_TIME_OUT);

    }


    private void test() {
        Log.e("UUID", Session.INSTANCE.getCurrentUserID());
        Notificacions not = new Notificacions();
        String uuid = UUID.randomUUID().toString();
        not.setIdNotificacion(uuid);
        not.setFromID(Session.INSTANCE.getCurrentUserID());
        not.setToID(Session.INSTANCE.getCurrentUserID());
        NotificationData.INSTANCE.saveFriendRequest(not);
        Log.e("UUID - request", uuid);

        NotificationData.INSTANCE.getData().observeForever( notificacions -> {
            for (Notificacions n : notificacions) {
                Log.e("UUID dadadasdasd", n.getIdNotificacion());
            }
        });


    }




}
