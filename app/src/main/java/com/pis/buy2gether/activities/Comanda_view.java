package com.pis.buy2gether.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.pis.buy2gether.R;

public class Comanda_view extends AppCompatActivity {
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_view);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
    }
}