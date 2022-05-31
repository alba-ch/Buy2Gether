package com.pis.buy2gether.usecases.home.favoriteList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Favorite;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private List<Favorite> favoriteList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    FavoriteListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        favoriteList = new ArrayList<>();
    }
    public void updateList(ArrayList<Favorite> favorites) {
        this.favoriteList.clear();
        this.favoriteList = favorites;
        notifyDataSetChanged();
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_favorite_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Favorite preferit = favoriteList.get(position);
        holder.myTextView.setText(preferit.getName());
        holder.checked.setChecked(false);
        //holder.imageView.setImageBitmap(preferit.getImage());
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void setList(MutableLiveData<ArrayList<Favorite>> favoriteList) {
        favoriteList.observeForever(list ->{
            if (list != null)
                this.favoriteList = list;
        });
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView imageView;
        RadioButton checked;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.txt_favorite_product);
            checked = itemView.findViewById(R.id.rdbtn_favorite_SEL);
            imageView = itemView.findViewById(R.id.img_favorite_product);
            myTextView.setClickable(true);
            myTextView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == -1) {
                checked.setChecked(!checked.isChecked());
                getItem(getAdapterPosition()).setChecked(checked.isChecked());
            } else
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Favorite getItem(int position) {
        return favoriteList.get(position);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}