package com.pis.buy2gether.usecases.home.user.friends;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentFriendsBinding;
import com.pis.buy2gether.model.domain.data.UserData;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.User;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import com.pis.buy2gether.usecases.home.user.address.AddressListAdapter;
import com.pis.buy2gether.usecases.home.user.settings.SettingsFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendsFragment extends Fragment implements FriendsListAdapter.ItemClickListener,UsersListAdapter.ItemClickListener {

    private FriendsListAdapter friendsListAdapter;
    private UsersListAdapter usersListAdapter;

    private FriendsViewModel viewModel;
    private FragmentFriendsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        friendsListAdapter = new FriendsListAdapter(getContext(), new ArrayList<>());
        usersListAdapter = new UsersListAdapter(getContext(), new ArrayList<>());
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        // Set onClickListeners
        setListeners();

        viewModel = new FriendsViewModel(getContext(),friendsListAdapter,usersListAdapter, binding);
        View root = binding.getRoot();

        // Set up view and MutableLiveData
        viewModel.setup();

        // set up the RecyclerView
        viewModel.setListUsers();

        // Query listeners for user search
        binding.searchViewFriends.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { viewModel.queryTextSubmit(query); return false; }
            @Override
            public boolean onQueryTextChange(String newText) { viewModel.queryTextChange(newText); return false; }
        });

        // Display Friend list
        viewModel.setList();

        return root;
    }

    /* ---- Listeners ---- */
    private void setListeners(){
        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnAmics.setOnClickListener(this::onClick);
        binding.btnSettings.setOnClickListener(this::onClick);
        binding.btnLan.setEnabled(false);
        binding.btnLan.setVisibility(View.INVISIBLE);

        friendsListAdapter.setClickListener(this);
        usersListAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.btn_return:
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.friends, new UserFragment()).commit();
                break;
            case R.id.btn_amics:
                Toast.makeText(getActivity(), "CERCA D'AMICS", Toast.LENGTH_SHORT).show();
                viewModel.setListUsers();
                break;
            case R.id.btn_settings:
                //change to settings Fragment
                fragmentTransaction.replace(R.id.useropt, new SettingsFragment());
                fragmentTransaction.addToBackStack("friends").commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, String friendshipID) {
        viewModel.deleteFriend(friendshipID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void sendRequest(View view, String userID) {
        viewModel.sendRequest(userID);
    }
}