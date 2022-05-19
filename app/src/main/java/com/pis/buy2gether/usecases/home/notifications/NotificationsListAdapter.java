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
import com.pis.buy2gether.model.domain.pojo.Notificacions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {


    public void updateNotificacions(ArrayList<Notificacions> list) {

    }

    enum notiType {FRIEND_REQUEST,GROUP_INVITE,PURCHASE_REVIEW}
    private LinkedHashMap<String,String> fromUserName;
    private LinkedHashMap<String,String> groupName;
    private LinkedHashMap<String,String> specialID;
    private LinkedHashMap<String,notiType> mType;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    NotificationsListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.fromUserName = new LinkedHashMap<>();
        this.mType = new LinkedHashMap<>();
        this.groupName = new LinkedHashMap<>();
        this.specialID = new LinkedHashMap<>();
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
        String id = (String) fromUserName.keySet().toArray()[position];

        switch (mType.get(id)){
            case GROUP_INVITE:
                holder.myTextView.setText(fromUserName.get(id) + " has invited you to group: " + groupName.get(id));
                break;
            case FRIEND_REQUEST:
                holder.myTextView.setText(fromUserName.get(id) + " has requested to be your friend");
                break;
            default:
                break;
        }
        holder.acceptButton.setVisibility(View.INVISIBLE);
        holder.denyButton.setVisibility(View.VISIBLE);

    }


    public void addNotification(notiType type, String id, String fromUser, String information, String specialIDStr){

        fromUserName.put(id,fromUser);

        groupName.put(id,information);
        specialID.put(id,specialIDStr);


        mType.put(id,type);

        notifyItemInserted(fromUserName.size());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return fromUserName.size();
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
        return fromUserName.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void swipe(String notiID, int adapterPosition) {
        fromUserName.remove(notiID);
        groupName.remove(notiID);
        specialID.remove(notiID);
        notifyItemRemoved(adapterPosition);
    }


    public String getExtraID(int adapterPosition) {

        return (String) specialID.values().toArray()[adapterPosition];
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