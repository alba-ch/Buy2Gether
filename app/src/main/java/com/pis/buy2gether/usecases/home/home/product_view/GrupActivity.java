package com.pis.buy2gether.usecases.home.home.product_view;



import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;

public class GrupActivity extends AppCompatActivity {
    private String product_id;
    private ImageButton back;
    private RecyclerView valoracion;
    private GrupViewModel viewModel;
    private ShapeableImageView image;
    private TextView description;
    private TextView precio;
    private Button unirse;
    private ImageButton fav;
    private TextView nom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product_id = extras.getString("product_id");
        }

        viewModel = new GrupViewModel(product_id);
        //binding();


        setContentView(R.layout.activity_product_view);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        valoracion = findViewById(R.id.lista_valoraciones);
        description = findViewById(R.id.descripcion);



        ArrayList<String> valoraciones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            valoraciones.add("No disponible a la fase alpha");
        }

        ValoracionsAdapter adapter = new ValoracionsAdapter(valoraciones);
        valoracion.setLayoutManager(new LinearLayoutManager(this));
        valoracion.setAdapter(adapter);
        observeView(viewModel);
    }

    private void binding() {
        image = findViewById(R.id.foto_producto);
        unirse = findViewById(R.id.btn_unirse);
        precio = findViewById(R.id.precios);
        description = findViewById(R.id.descripcion);
        fav = findViewById(R.id.btn_favorite);
        nom = findViewById(R.id.nom_prducte);


    }


    private void observeView(GrupViewModel viewModel) {
        MutableLiveData<Grup> grup = viewModel.getLiveGrup();
        grup.observe(this, g -> {
            binding();
            viewModel.setGrup(g);
            unirse.setOnClickListener(v -> {
                viewModel.addGrup();
            } );
            fav.setOnClickListener(v->{
                viewModel.favGrup();
            });
            nom.setText(g.getName().toUpperCase());

            unirse.setText("UNIR-SE GRUP");
            String descriptio = viewModel.getDescription();
            if (descriptio == null || descriptio.equals("")){
                descriptio = "No hi ha descripció";}
            description.setText(descriptio);
            precio.setText(viewModel.getPrecio());
            MutableLiveData<Bitmap> bitmap = viewModel.getPhoto();
            bitmap.observe(this, b->{
                image.setImageBitmap(b);
            });
        });


    }
}