package com.pis.buy2gether.ui.home;

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
import com.pis.buy2gether.activities.product_view.Product_view;

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
        holder.imageView.setImageResource(currentItem.getProduct_image());
        holder.textView.setText(currentItem.getProduct_name());


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Inner class to reuse View
     */
    public static class TypeRvViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        AppCompatImageButton button;

        public TypeRvViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_product);
            textView = itemView.findViewById(R.id.name_product);
            button = itemView.findViewById(R.id.boton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(act, Product_view.class);
                    act.startActivity(i);}
            });

        }
    }
}
