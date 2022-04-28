package com.pis.buy2gether.usecases.home.favoriteList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.databinding.FragmentFavoriteListBinding;
import com.pis.buy2gether.usecases.home.notifications.NotificationsListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteListAdapter.ItemClickListener, View.OnClickListener {

    private FavoriteViewModel favoriteViewModel;
    private FavoriteListAdapter favoriteListAdapter;
    private FragmentFavoriteListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnSubmit.setOnClickListener(this);


        // set up the RecyclerView
        /*RecyclerView recyclerView = binding.favoriteList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteListAdapter = new FavoriteListAdapter(getContext(), items);
        recyclerView.setAdapter(favoriteListAdapter);
        favoriteListAdapter.setClickListener(this);
        binding.favoriteList.setAdapter(favoriteListAdapter);*/

        setList();
        return root;
    }

    public void deleteFavorite(int position) {
        String ID = favoriteListAdapter.getItem(position);
        //favoriteViewModel.delete(ID);
        favoriteListAdapter.removeGroup(ID,position);
    }
    private void setList(){
        // set up the RecyclerView
        RecyclerView recyclerView = binding.favoriteList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteListAdapter = new FavoriteListAdapter(getContext());
        recyclerView.setAdapter(favoriteListAdapter);
        favoriteListAdapter.setClickListener(this);
        binding.favoriteList.setAdapter(favoriteListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                switch(swipeDir){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        deleteFavorite(viewHolder.getAdapterPosition());
                        break;
                    default:
                        break;
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Task<DocumentSnapshot> task = favoriteViewModel.getFavorite().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("Favorite") != null) {
                    ArrayList<String> favoriteList = new ArrayList<>(((List<String>) documentSnapshot.get("Favorite")));
                    for (String groupID : favoriteList) {
                        Task<DocumentSnapshot> task = favoriteViewModel.getGroup(groupID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot1) {
                                StorageReference mImageRef = FirebaseStorage.getInstance().getReference("groupImages/" + groupID + ".jpeg");
                                final long ONE_MEGABYTE = 1024 * 1024;
                                mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        favoriteListAdapter.addGroup(bm, groupID, documentSnapshot1.get("Product Name").toString());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(getActivity(), "Error al carregar l'imatge de group\n" + exception, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });

    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + favoriteListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int offset = 0, size = favoriteListAdapter.getItemCount();
        if(view.getId() == binding.btnSubmit.getId()){
            for(int i  = 0; i < size; i ++) {
                if(favoriteListAdapter.isChecked(i-offset)) {
                    deleteFavorite(i - offset);
                    offset++;
                }
            }
        }
    }
}