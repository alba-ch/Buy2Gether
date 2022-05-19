package com.pis.buy2gether.usecases.home.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.databinding.FragmentNotificationsBinding;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.usecases.home.user.address.AddressListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        setList();

        MutableLiveData<ArrayList<Notificacions>> notificacions = notificationsViewModel.getNotificacions();

        notificacions.observe(this, list ->{
            if(list != null){
                notificationsListAdapter.updateNotificacions(list);
            }
        });


        return root;
    }

    private void setList(){
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
                notificationsListAdapter.swipe(notificationsListAdapter.getNotiID(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Task task = notificationsViewModel.getFriendRequests().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Task<DocumentSnapshot> task = notificationsViewModel.getUserName((String)documentSnapshot.get("fromID")).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot1) {
                                notificationsListAdapter.addNotification(NotificationsListAdapter.notiType.FRIEND_REQUEST,documentSnapshot.getId(),(String) documentSnapshot1.get("username"),"",(String)documentSnapshot.get("fromID"));

                            MainActivity.changeNotificationBadge(MainActivity.getNotificationBadge()-1);
                        }
                    });
                }
            }
        });
        task = notificationsViewModel.getGroupInvites().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Task<DocumentSnapshot> task = notificationsViewModel.getGroup((String)documentSnapshot.get("GroupID")).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot1) {
                                Task<DocumentSnapshot> task = notificationsViewModel.getUserName((String) documentSnapshot.getData().get("fromUser")).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot2) {
                                    notificationsListAdapter.addNotification(NotificationsListAdapter.notiType.GROUP_INVITE,documentSnapshot.getId(),(String)documentSnapshot2.get("username"),(String)documentSnapshot1.get("Product Name"),documentSnapshot1.getId());

                                    MainActivity.changeNotificationBadge(MainActivity.getNotificationBadge()-1);
                                }
                            });

                        }
                    });

                }
            }
        });
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
        MainActivity.changeNotificationBadge(MainActivity.getNotificationBadge()-1);
    }
    private void denyNoti(NotificationsListAdapter.notiType notiType, String notiID) {
        Toast.makeText(getContext(), "denying noti" + notiID, Toast.LENGTH_SHORT).show();
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
        MainActivity.changeNotificationBadge(MainActivity.getNotificationBadge()-1);
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