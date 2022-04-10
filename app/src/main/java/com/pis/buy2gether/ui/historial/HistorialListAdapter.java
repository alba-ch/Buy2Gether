package com.pis.buy2gether.ui.historial;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.ui.friends.FriendsListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HistorialListAdapter extends RecyclerView.Adapter<HistorialListAdapter.ViewHolder> {




    private ArrayList<String> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.pd_name);
        }

        public void asignarNombre(String nombre){
            product.setText(nombre);
        }
    }

    public HistorialListAdapter(ArrayList<String> mData) {
        this.mData = mData;
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