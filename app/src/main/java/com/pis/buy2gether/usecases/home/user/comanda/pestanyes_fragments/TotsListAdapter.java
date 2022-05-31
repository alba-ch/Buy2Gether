package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.usecases.home.home.TypeRvViewModel;
import com.pis.buy2gether.usecases.home.user.comanda.comanda_view.ComandaActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TotsListAdapter extends RecyclerView.Adapter<TotsListAdapter.ViewHolder> {

    private ArrayList<Grup> mData;
    private Activity act;

    public class ViewHolder extends RecyclerView.ViewHolder{
        String product_id;
        TextView product_name;
        TextView product_price;
        TextView product_proces;
        ImageView product_image;
        TextView product_date;
        AppCompatImageButton product_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_price = itemView.findViewById(R.id.price);
            product_proces = itemView.findViewById(R.id.pd_proces);
            product_name = itemView.findViewById(R.id.pd_name);
            product_click = itemView.findViewById(R.id.click_button);
            product_date = itemView.findViewById(R.id.pd_date);
            product_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, ComandaActivity.class);
                    intent.putExtra("Grup", product_id);
                    act.startActivity(intent);

                }
            });
            product_image = itemView.findViewById(R.id.image);
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

        public void setImageBitmap(Bitmap bitmap){
            product_image.setImageBitmap(bitmap);
        }
        private void setDate(String date){
            product_date.setText(date);
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
        if (newData == null)
            return;
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
        holder.setPrice(String.valueOf(comanda.getPrice()));
        String proces = "NONE";
        switch (comanda.getProces()) {
            case 0:
                proces = "EN PROCÈS";
                break;
            case 1:
                proces = "FINALITZAT";
                break;
            case 2:
                proces = "VALORAT";
                break;
            default:
                proces = "DESCONEGUT";
                break;

        }
        Date data = new Date();
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(data);
        holder.setDate(newstring);
        holder.setProces(proces);
        MutableLiveData<Bitmap> liveData = TypeRvViewModel.getPhoto(comanda.getId());
        liveData.observeForever( b ->{
            holder.setImageBitmap(b);
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}