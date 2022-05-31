package com.pis.buy2gether.usecases.home.home.product_view.group.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private List<User> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public FriendListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
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
        User user = mData.get(position);
        holder.myTextView.setText(user.getUsername());
        MutableLiveData<Bitmap> pfp = user.getProfileImage();

        if(pfp != null) {
            pfp.observeForever(b ->{
                holder.imageView.setImageBitmap(b);
            });
        }

    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(ArrayList<User> listFriends) {
        mData=listFriends;
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView myTextView;
        MaterialButton inviteButton;


        ViewHolder(View itemView) {
            super(itemView);
            inviteButton = itemView.findViewById(R.id.invitButton);
            myTextView = itemView.findViewById(R.id.Text);
            imageView = itemView.findViewById(R.id.inviteListPic);
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
    public User getUser(int index) {
        return mData.get(index);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}