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
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        viewModel = new FriendsViewModel(getContext(),binding);
        View root = binding.getRoot();

        // Set up view and MutableLiveData
        viewModel.setup();

        // Set onClickListeners
        setListeners();

        // Set up MutableLiveData lists
        MutableLiveData<ArrayList<User>> userList = viewModel.getUsers();
        MutableLiveData<ArrayList<User>> friendList = viewModel.getFriends();

        // First list shown - Friend list (to initially set up the RecyclerView)
        friendsListAdapter = new FriendsListAdapter(getContext(),friendList.getValue());
        setFriendsAdapter();

        // Observers for listAdapters
        userList.observe(this, list ->{
            if(list != null){
                Toast.makeText(getContext(), "Updating user list", Toast.LENGTH_SHORT).show();
                usersListAdapter = new UsersListAdapter(getContext(),list);
                //setUsersAdapter(); <-- Lo he quitado porque teóricamente actualizamos solo cuando el usuario elige mostrar la lista.
                // (+) Debería bastar modificar el listAdapter (cambiar list) para que cuando hagamos setXAdapter() - en onClick y onItemClick-
                //     contenga los cambios, igualmente de la otra forma tampoco me funciona.
            }
        });
        friendList.observe(this, list ->{
            if(list != null){
                Toast.makeText(getContext(), "Updating friend list", Toast.LENGTH_SHORT).show();
                friendsListAdapter = new FriendsListAdapter(getContext(),list);
            }
        });

        // Query listeners for user search
        binding.searchViewFriends.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { queryTextSubmit(query); return false; }
            @Override
            public boolean onQueryTextChange(String newText) { queryTextChange(newText); return false; }
        });

        return root;
    }

    /* ---- RecyclerView listAdapters ---- */
    private void setUsersAdapter(){
        binding.friendsList.setAdapter(usersListAdapter);
        binding.friendsList.setLayoutManager(new LinearLayoutManager(getContext()));
        usersListAdapter.setClickListener(this);
        Toast.makeText(getContext(), "ADAPTER USERS", Toast.LENGTH_SHORT).show();
        viewModel.showSearch();
    }

    private void setFriendsAdapter(){
        binding.friendsList.setAdapter(friendsListAdapter);
        binding.friendsList.setLayoutManager(new LinearLayoutManager(getContext()));
        friendsListAdapter.setClickListener(this);
        Toast.makeText(getContext(), "ADAPTER FRIENDS", Toast.LENGTH_SHORT).show();
        viewModel.hideSearch();
    }

    /* ---- Query functions ---- */
    public void queryTextSubmit(String query){
        // User prem enter
        binding.searchViewFriends.setQuery(query,false);
        binding.searchViewFriends.clearFocus();
        usersListAdapter.getFilter().filter(query);
    }

    public void queryTextChange(String newText){
        usersListAdapter.getFilter().filter(newText);
    }

    /* ---- Listeners ---- */
    private void setListeners(){
        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnAmics.setOnClickListener(this::onClick);
        binding.btnSettings.setOnClickListener(this::onClick);
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
                setUsersAdapter(); // Display user list
                break;
            case R.id.btn_settings:
                Toast.makeText(getActivity(), "SETTINGS", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.friends, new SettingsFragment()).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, String friendshipID) {
        viewModel.deleteFriend(friendshipID);
        setFriendsAdapter(); // Display friend list
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void sendRequest(View view, String userID) {
        viewModel.sendRequest(view, userID);
    }
}