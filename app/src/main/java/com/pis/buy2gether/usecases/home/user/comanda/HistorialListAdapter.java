package com.pis.buy2gether.usecases.home.user.comanda;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.home.user.comanda.comanda_view.Comanda_view;

import java.util.ArrayList;


public class HistorialListAdapter extends RecyclerView.Adapter<HistorialListAdapter.ViewHolder> {




    private ArrayList<String> mData;
    private Activity act;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product;

        AppCompatImageButton product_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.pd_name);
            product_click = itemView.findViewById(R.id.click_button);
            product_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, Comanda_view.class);
                    act.startActivity(intent);

                }
            });
        }

        public void asignarNombre(String nombre){
            product.setText(nombre);
        }
    }

    public HistorialListAdapter(ArrayList<String> mData, Activity activity) {
        this.mData = mData;
        act = activity;
    }

    @NonNull
    @Override
    public HistorialListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialListAdapter.ViewHolder holder, int position) {
        holder.asignarNombre(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}