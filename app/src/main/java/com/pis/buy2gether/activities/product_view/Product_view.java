package com.pis.buy2gether.activities.product_view;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.pis.buy2gether.R;

import java.util.ArrayList;

public class Product_view extends AppCompatActivity {
    private ImageButton back;
    private RecyclerView valoracion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        valoracion = findViewById(R.id.lista_valoraciones);

        ArrayList<String> valoraciones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            valoraciones.add("Hola" + i);
        }
        ValoracionsAdapter adapter = new ValoracionsAdapter(valoraciones);
        valoracion.setLayoutManager(new LinearLayoutManager(this));
        valoracion.setAdapter(adapter);





    }
}