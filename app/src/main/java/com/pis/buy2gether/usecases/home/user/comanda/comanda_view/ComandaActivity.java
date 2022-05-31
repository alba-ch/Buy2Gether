package com.pis.buy2gether.usecases.home.user.comanda.comanda_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.Date;

public class ComandaActivity extends AppCompatActivity {
    private ImageButton back;
    private String product_id;
    private ImageView product_image;
    private TextView product_price;
    private TextView product_name;
    private TextView data;
    private TextView comanda;
    private ComandaViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_view);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product_id = extras.getString("Grup");
        }

        viewModel = new ComandaViewModel(product_id);

        product_image= findViewById(R.id.imageView3);
        product_name = findViewById(R.id.nom_producte);
        product_price =  findViewById(R.id.preu_producte);
        data = findViewById(R.id.data_info);
        comanda = findViewById(R.id.comanda_info);

        MutableLiveData<Grup> grup = viewModel.getGrup();

        observeViewModel(grup);
    }

    public void observeViewModel(MutableLiveData<Grup> g){
        g.observe(this, grup ->{
            viewModel.setGrup(grup);
            viewModel.getProductPhoto().observe(this, bm ->{
                product_image.setImageBitmap(bm);
            });
            product_name.setText(viewModel.getProductName());
            product_price.setText(viewModel.getProductPrice() + " €");
            comanda.setText(grup.getId());
            Date date = new Date();
            data.setText(date.toString());
        });
    }
}