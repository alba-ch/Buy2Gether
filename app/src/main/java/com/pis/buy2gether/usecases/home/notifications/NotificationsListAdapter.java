package com.pis.buy2gether.usecases.home.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.home.home.product_view.group.share.FriendListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {


    enum notiType {FRIEND_REQUEST,GROUP_INVITE,PURCHASE_REVIEW}
    private LinkedHashMap<String,String> mData;
    private LinkedHashMap<String,String> info;
    private LinkedHashMap<String,notiType> mType;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    NotificationsListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new LinkedHashMap<>();
        this.mType = new LinkedHashMap<>();
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String id = (String) mData.keySet().toArray()[position];

        switch (mType.get(id)){
            case GROUP_INVITE:
                holder.myTextView.setText(mData.get(id) + " has invited you to group: " + info.get(id));
                break;
            case FRIEND_REQUEST:
                holder.myTextView.setText(mData.get(id) + " has requested to be your friend");
                break;
            default:
                break;
        }
        holder.acceptButton.setVisibility(View.INVISIBLE);
        holder.denyButton.setVisibility(View.VISIBLE);

    }


    public void addNotification(notiType type, String id, String fromUser, String information){

        mData.put(id,fromUser);

        info.put(id,information);

        mType.put(id,type);

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
        ImageView acceptButton;
        ImageView denyButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Text);
            acceptButton = itemView.findViewById(R.id.accept_tick);
            denyButton = itemView.findViewById(R.id.deny_cross);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void swipe(ViewHolder viewHolder) {
        mData.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }


    public String getExtraID(int adapterPosition) {

        return (String) info.values().toArray()[adapterPosition];
    }

    public notiType getNotiType(int position) {
        return (notiType) mType.values().toArray()[position];
    }

    public String getNotiID(int position) {
        return (String) mType.keySet().toArray()[position];
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}