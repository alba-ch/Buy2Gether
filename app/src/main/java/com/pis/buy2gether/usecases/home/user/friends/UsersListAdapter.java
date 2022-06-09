package com.pis.buy2gether.usecases.home.user.friends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.User;
import com.pis.buy2gether.model.session.Session;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> implements Filterable {

    private List<User> mData;
    private List<User> allUsers;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Data is passed into the constructor
    UsersListAdapter(Context context, ArrayList<User> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.allUsers = new ArrayList<>(); // Copy to preserve original user list after filtering query
        allUsers.addAll(data);
    }

    // Inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        User user =  mData.get(position);
        MutableLiveData<Bitmap> pfp = ImageData.INSTANCE.getProfilePhoto(user.getId());
        pfp.observeForever(b ->{
            if(b != null)
                holder.pfp.setImageBitmap(b);
        });
        holder.myTextView.setText(mData.get(position).getUsername());
        holder.selectButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<User> filteredUserList = new ArrayList<>();
                if (charSequence.toString().isEmpty()) {
                    filteredUserList.addAll(allUsers);
                } else {
                    for (int i=0; i<allUsers.size(); i++) {
                        String username = allUsers.get(i).getUsername();
                        if (username.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredUserList.add(allUsers.get(i));
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mData.clear();

                mData.addAll((Collection<? extends User>) filterResults.values);
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        RadioButton selectButton;
        ShapeableImageView pfp;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.name);
            pfp = itemView.findViewById(R.id.img_friend);
            selectButton = itemView.findViewById(R.id.friend_SEL);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == -1){
                selectButton.setChecked(!selectButton.isChecked());
                // Change item background color
                if(selectButton.isChecked()){
                    itemView.setBackgroundColor(Color.parseColor("#E7D3F4"));
                    selectButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    myTextView.setTextColor(Color.BLACK);
                    if (mClickListener != null) mClickListener.sendRequest(view, mData.get(getAdapterPosition()).getId());
                }
                else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {itemView.setBackground(itemView.getForeground());}
                    else{itemView.setBackgroundColor(Color.TRANSPARENT);}
                    selectButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    myTextView.setTextColor(Color.parseColor("#707070"));
                }
            }
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void sendRequest(View view, String userID);
        void onClick(View view);
    }

    // Not necessary
    public void addUser(User user){
        mData.add(user);
        allUsers.add(user);
        notifyItemInserted(mData.size());
    }

    public void clear(){
        mData.clear();
        allUsers.clear();
    }

/*
    // total number of rows
    @Override
    public int getItemCount() {
        return user.size();
    }*/

    // convenience method for getting data at click position
    /*
    String getItem(int id) {
        return user.get(id);
    }

    // convenience method for getting all the usernames
    List<String> getList(){
        return  Arrays.asList(user.values().toString());
    }*/

    // allows clicks events to be caught
}