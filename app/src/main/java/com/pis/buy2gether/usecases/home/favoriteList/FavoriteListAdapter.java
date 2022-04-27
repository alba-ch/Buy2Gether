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
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private LinkedHashMap<String,String> groups;
    private LinkedHashMap<String,Bitmap> groupImage;
    private LinkedHashMap<String,Boolean> groupChecked;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    FavoriteListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        groups = new LinkedHashMap<>();
        groupImage = new LinkedHashMap<>();
        groupChecked = new LinkedHashMap<>();
    }

    public void addGroup(Bitmap bitmap, String ID, String group) {
        groups.put(ID,group);
        groupImage.put(ID,bitmap);
        groupChecked.put(ID,false);
        notifyItemInserted(getItemCount());
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_favorite_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String ID = (String) groups.keySet().toArray()[position];
        holder.myTextView.setText(groups.get(ID));
        holder.checked.setChecked(false);
        holder.imageView.setImageBitmap(groupImage.get(ID));
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return groups.size();
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
                groupChecked.put(getItem(getAdapterPosition()),checked.isChecked());
            } else
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void removeGroup(String ID, int position){
        groups.remove(ID);
        groupImage.remove(ID);
        groupChecked.remove(ID);
        notifyItemRemoved(position);
    }
    boolean isChecked(int position) {
        return (boolean) groupChecked.values().toArray()[position];
    }

    // convenience method for getting data at click position
    String getItem(int position) {
        return (String) groups.keySet().toArray()[position];
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