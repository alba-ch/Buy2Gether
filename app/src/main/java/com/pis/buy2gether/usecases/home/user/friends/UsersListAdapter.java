package com.pis.buy2gether.usecases.home.user.friends;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private LinkedHashMap<String,String> user;
    private LinkedHashMap<String,String> pfp;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int row_idx = -1;

    // data is passed into the constructor
    UsersListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.user= new LinkedHashMap<>();
        this.pfp = new LinkedHashMap<>();
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String id = (String) user.keySet().toArray()[position];

        holder.myTextView.setText(user.get(id));
        // Modifiquem la foto de perfil (per implementar)

        //holder.acceptButton.setVisibility(View.INVISIBLE);
        holder.selectButton.setVisibility(View.VISIBLE);
    }

    public void addUser(String userID, String username){
        user.put(userID,username);
        notifyItemInserted(user.size());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return user.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        RadioButton selectButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.name);
            selectButton = itemView.findViewById(R.id.friend_SEL);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == -1){
                selectButton.setChecked(!selectButton.isChecked());
                // change item background color
                if(selectButton.isChecked()){
                    itemView.setBackgroundColor(Color.parseColor("#E7D3F4"));
                    selectButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    myTextView.setTextColor(Color.BLACK);
                }
                else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {itemView.setBackground(itemView.getForeground());}
                    else{itemView.setBackgroundColor(Color.TRANSPARENT);}
                    selectButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    myTextView.setTextColor(Color.parseColor("#707070"));
                }
            }
            if (mClickListener != null) mClickListener.sendRequest(view, (String) user.keySet().toArray()[getAdapterPosition()]);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return user.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /*public void swipe(ViewHolder viewHolder) {
        usernameFriend.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }*/
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void sendRequest(View view, String userID);
        void onClick(View view);
    }
}