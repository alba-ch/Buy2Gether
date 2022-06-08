package com.pis.buy2gether.usecases.home.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.usecases.home.home.product_view.GrupActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TypeRvAdapter extends RecyclerView.Adapter<TypeRvAdapter.TypeRvViewHolder>{

    private ArrayList<Grup> originalList;
    private ArrayList<Grup> products;

    private String currentFilter = "";
    private static Activity act;

    public TypeRvAdapter(ArrayList<Grup> products, Activity activity) {
        this.originalList = products;
        this.products = new ArrayList<>(originalList);
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

    public void updateList(ArrayList<Grup> products) {
        this.products.clear();
        this.originalList.clear();
        this.originalList = products;
        this.products = new ArrayList<>(originalList);
        changeFilter(currentFilter);
        notifyDataSetChanged();
    }

    public void changeFilter(String newFilter) {
        currentFilter = newFilter;
        this.products.clear();
        for(Grup grup : originalList)
            if(grup.getName().toLowerCase().contains(currentFilter.toLowerCase()))
                products.add(grup);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull TypeRvViewHolder holder, int position) {
        //assign values to components when initializing and sliding recyclerView

        Grup grup = products.get(position);
        MutableLiveData<Bitmap> liveData = TypeRvViewModel.getPhoto(grup.getId());
        liveData.observeForever( b ->{
            holder.product_image.setImageBitmap(b);
        });
        holder.product_name.setText(grup.getName());
        holder.product_price.setText(String.valueOf(grup.getPrice()) + " €");
        holder.setId(grup.getId());


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
        private String product_id;

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
                    i.putExtra("product_id", product_id);
                    act.startActivity(i);}
            });
        }

        public void setId(String id){
            product_id = id;
        }

        public String getId(){
            return product_id;
        }

    }
}
