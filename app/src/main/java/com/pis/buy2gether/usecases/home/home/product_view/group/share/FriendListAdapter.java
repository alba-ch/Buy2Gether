package com.pis.buy2gether.usecases.home.home.product_view.group.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.pis.buy2gether.R;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private LinkedHashMap<String,String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public FriendListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new LinkedHashMap<>();
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_share_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String user = (String) mData.keySet().toArray()[position];
        holder.myTextView.setText(user);

    }

    public void addUser(String userID, String Username){
        mData.put(Username,userID);
        notifyItemInserted(mData.size());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        MaterialButton inviteButton;


        ViewHolder(View itemView) {
            super(itemView);
            inviteButton = itemView.findViewById(R.id.invitButton);
            myTextView = itemView.findViewById(R.id.Text);
            itemView.setOnClickListener(this);
            inviteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            inviteButton.setVisibility(View.INVISIBLE);
        }
    }
    // convenience method for getting data at click position
    public String getUserID(int index) {
        return (String) mData.values().toArray()[index];
    }
    // convenience method for getting data at click position
    public String getUserName(int index) {
        return (String) mData.keySet().toArray()[index];
    }

    // convenience method for getting data at click position
    String getUserID(String username) {
        return mData.get(username);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}