package com.pis.buy2gether.usecases.home.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.home.home.product_view.GrupActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TypeRvAdapter extends RecyclerView.Adapter<TypeRvAdapter.TypeRvViewHolder>{

    private ArrayList<TypeTabModel> products;
    private static Activity act;

    public TypeRvAdapter(ArrayList<TypeTabModel> products, Activity activity) {
        this.products = products;
        act = activity;
    }

    @NonNull
    @NotNull
    @Override
    public TypeRvViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //create a View for the first time and bind with the especific layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_rv_item, parent,false);

        //we will load data from holder and will not recreate views
        TypeRvViewHolder typeRvViewHolder = new TypeRvViewHolder(view);
        return typeRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TypeRvViewHolder holder, int position) {
        //assign values to components when initializing and sliding recyclerView
        TypeTabModel currentItem = products.get(position);
        holder.product_image.setImageResource(currentItem.getProduct_image());
        holder.product_name.setText(currentItem.getProduct_name());
        holder.product_price.setText(currentItem.getProduct_price());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Inner class to reuse View
     */
    public static class TypeRvViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_name;
        AppCompatImageButton product_button;
        TextView product_price;

        public TypeRvViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.img_product);
            product_name = itemView.findViewById(R.id.name_product);
            product_button = itemView.findViewById(R.id.boton);
            product_price = itemView.findViewById(R.id.price_product);

            product_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(act, GrupActivity.class);
                    act.startActivity(i);}
            });
        }
    }
}
