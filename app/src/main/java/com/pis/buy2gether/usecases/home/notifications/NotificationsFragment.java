package com.pis.buy2gether.usecases.home.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.databinding.FragmentNotificationsBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements NotificationsListAdapter.ItemClickListener {

    private NotificationsViewModel notificationsViewModel;
    private NotificationsListAdapter notificationsListAdapter;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<String> items = new ArrayList<>();
        items.add("Horse");


        // set up the RecyclerView
        RecyclerView recyclerView = binding.notificationsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsListAdapter = new NotificationsListAdapter(getContext(), this);
        recyclerView.setAdapter(notificationsListAdapter);
        notificationsListAdapter.setClickListener(this);
        binding.notificationsList.setAdapter(notificationsListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                notificationsListAdapter.swipe((NotificationsListAdapter.ViewHolder) viewHolder);
                switch(swipeDir){
                    case ItemTouchHelper.LEFT:
                        denyNoti(notificationsListAdapter.getNotiType(viewHolder.getAdapterPosition()), notificationsListAdapter.getNotiID(viewHolder.getAdapterPosition()));
                        break;
                    case ItemTouchHelper.RIGHT:
                        acceptNoti(notificationsListAdapter.getNotiType(viewHolder.getAdapterPosition()), notificationsListAdapter.getNotiID(viewHolder.getAdapterPosition()), notificationsListAdapter.getExtraID(viewHolder.getAdapterPosition()));
                        break;
                    default:
                        break;
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
       
        return root;
    }

    private void acceptNoti(NotificationsListAdapter.notiType notiType, String notiID, String extraID) {
        switch(notiType){
            case GROUP_INVITE:
                notificationsViewModel.joinGroup(notificationsViewModel.getUser(),extraID);
                notificationsViewModel.removeGroupInvite(notiID);
                break;
            case FRIEND_REQUEST:
                notificationsViewModel.addFriend(notificationsViewModel.getUser(),extraID);
                notificationsViewModel.removeFriendRequest(notiID);
                break;
            default:
                break;
        }
    }
    private void denyNoti(NotificationsListAdapter.notiType notiType, String notiID) {
        switch(notiType){
            case GROUP_INVITE:
                notificationsViewModel.removeGroupInvite(notiID);
                break;
            case FRIEND_REQUEST:
                notificationsViewModel.removeFriendRequest(notiID);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}