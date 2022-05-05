package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

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
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.usecases.home.user.comanda.comanda_view.Comanda_view;

import java.util.ArrayList;


public class TotsListAdapter extends RecyclerView.Adapter<TotsListAdapter.ViewHolder> {

    private ArrayList<Grup> mData;
    private Activity act;

    public class ViewHolder extends RecyclerView.ViewHolder{
        String product_id;
        TextView product_name;
        TextView product_price;
        TextView product_proces;
        AppCompatImageButton product_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_price = itemView.findViewById(R.id.price);
            product_proces = itemView.findViewById(R.id.pd_proces);
            product_name = itemView.findViewById(R.id.pd_name);
            product_click = itemView.findViewById(R.id.click_button);
            product_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, Comanda_view.class);
                    act.startActivity(intent);

                }
            });
        }

        public String getId(){
            return product_id;
        }

        public void setId(String id){
            product_id = id;
        }

        public void setName(String name){
            product_name.setText(name);
        }

        public void setPrice(String price){
            product_price.setText(price);
        }

        public void setProces(String proces){
            product_proces.setText(proces);
        }
    }

    public TotsListAdapter(ArrayList<Grup> mData, Activity activity) {
        this.mData = mData;
        act = activity;
    }

    public TotsListAdapter(Activity act){
        mData = new ArrayList<Grup>();
        this.act = act;
    }

    public void updateList(final ArrayList<Grup> newData) {
        this.mData.clear();
        this.mData = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TotsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TotsListAdapter.ViewHolder holder, int position) {
        Grup comanda = mData.get(position);
        holder.setId(comanda.getId());
        holder.setName(comanda.getName());
        holder.setPrice(String.valueOf(comanda.getPrice()));;
        holder.setProces(String.valueOf(comanda.getProces()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}