package com.pis.buy2gether.usecases.home.user.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

    private List<Map> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    AddressListAdapter(Context context, List<Map> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_address_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.num_address.setText("Adreça "+(position+1));
        holder.name_address.setText(mData.get(position).get("Address name").toString());
        holder.address.setText(mData.get(position).get("Full address").toString());
        holder.postal_code.setText(mData.get(position).get("Zip code").toString());
        holder.tel.setText(mData.get(position).get("Telephone").toString());
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView num_address;
        TextView name_address;
        TextView address;
        TextView postal_code;
        TextView tel;

        ViewHolder(View itemView) {
            super(itemView);
            num_address = itemView.findViewById(R.id.txt_num_address);
            name_address = itemView.findViewById(R.id.txt_name_address);
            address = itemView.findViewById(R.id.txt_address);
            postal_code = itemView.findViewById(R.id.txt_postal_code);
            tel = itemView.findViewById(R.id.txt_tel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void swipe(ViewHolder viewHolder) {
        mData.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void onClick(View view);
    }
}