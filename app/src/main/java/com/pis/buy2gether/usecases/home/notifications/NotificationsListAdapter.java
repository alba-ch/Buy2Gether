package com.pis.buy2gether.usecases.home.notifications;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    //list of notifications
    private List<Notificacions> mData;

    // data is passed into the constructor
    NotificationsListAdapter(Context context, ItemClickListener itemClickListener, List<Notificacions> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = itemClickListener;
        this.mData = data;
    }

    public void updateNotificacions(ArrayList<Notificacions> list) {
        this.mData.clear();
        this.mData = list;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        /*
        * set textView of the holder according to the type of this notification
        * */
        Notificacions notificacions = mData.get(position);
        Log.e("NOTIFICATION"," mData size: " + mData.size());
        Log.e("NOTIFICATION"," we get the notification CLASS: " + notificacions.toString());
        Log.e("NOTIFICATION"," we get the notification group: " + notificacions.getGroupName());
        Log.e("NOTIFICATION"," we get the notification ID: " + notificacions.getIdNotificacion());
        Log.e("NOTIFICATION"," we get the notification fromUserName: " + notificacions.getFromUsername());
        Log.e("NOTIFICATION"," we get the notification fromID: " + notificacions.getFromID());
        Log.e("NOTIFICATION"," we get the notification TYPE: " + notificacions.getNotiType());

        switch(notificacions.getNotiType()){
            case GROUP_INVITE:
                holder.myTextView.setText(notificacions.getFromUsername() + " has invited you to group: " + notificacions.getGroupName());
                break;
            case FRIEND_REQUEST:
                holder.myTextView.setText(notificacions.getFromUsername() + " has requested to be your friend ");
                break;
            default:
                break;
        }
        holder.acceptButton.setVisibility(View.VISIBLE);
        holder.denyButton.setVisibility(View.VISIBLE);
    }


    /**
     * add a new Notification in our mData list
     * @param notiType
     * @param idNotification
     * @param fromUsername
     * @param groupname
     * @param fromId
     */
    public void addNotification(NotiType notiType, String idNotification, String fromUsername, String groupname, String fromId){

        /*
        * add notification with corresponding parametres to our notification list
        * */
        Notificacions notificacions = new Notificacions(notiType,idNotification,fromUsername,groupname,fromId);
        //mData.add(notificacions);

        notifyItemInserted(mData.size());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        //return fromUserName.size();
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

    // convenience method for getting data at click position (id of actual notification)
    String getItem(int position) {
        return mData.get(position).getIdNotificacion();
        //return fromUserName.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void swipe(int adapterPosition) {
        //remove notification from notification list
        mData.remove(adapterPosition);

    }

    /**
     * get fromID of notification in position adapterPosition in mData list
     * @param adapterPosition
     * @return
     */
    public String getExtraID(int adapterPosition) {
        return mData.get(adapterPosition).getFromID();
        //return (String) specialID.values().toArray()[adapterPosition];
    }

    /**
     * get the notification type of the notification in position "position" in mData list
     * @param position
     * @return
     */
    public NotiType getNotiType(int position) {
        return mData.get(position).getNotiType();
        //return (notiType) mType.values().toArray()[position];
    }

    /**
     * get the notification ID in position "position" in mData list
     * @param position
     * @return
     */
    public String getNotiID(int position) {
        return mData.get(position).getIdNotificacion();
        //return (String) mType.keySet().toArray()[position];
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}