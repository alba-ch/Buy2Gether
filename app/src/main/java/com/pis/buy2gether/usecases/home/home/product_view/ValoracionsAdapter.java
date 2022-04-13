package com.pis.buy2gether.usecases.home.home.product_view;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pis.buy2gether.R;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;



import java.util.ArrayList;



public class ValoracionsAdapter extends RecyclerView.Adapter<ValoracionsAdapter.ViewHolder> {


    private ArrayList<String> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.profileName);
        }

        public void asignarNombre(String nombre){
            product.setText(nombre);
        }
    }

    public ValoracionsAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }
    public void add(String nombre){
        mData.add(nombre);
    }

    @NonNull
    @Override
    public ValoracionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_valoracions_list_item, null, false);
        return new ValoracionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValoracionsAdapter.ViewHolder holder, int position) {
        holder.asignarNombre(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}