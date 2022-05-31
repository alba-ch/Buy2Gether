package com.pis.buy2gether.usecases.home.user.address;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentAddressBinding;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressFragment extends Fragment implements AddressListAdapter.ItemClickListener {

    private AddressViewModel addressViewModel;
    private AddressListAdapter addressListAdapter;
    private RecyclerView recyclerView;
    private FragmentAddressBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddressBinding.inflate(inflater, container, false);
        addressViewModel = new AddressViewModel(getContext(), inflater);
        View root = binding.getRoot();

        // Set onClickListeners
        setListeners();

        // Set up RecyclerView
        setupRV();

        // Display list of addresses with mutable live data
        MutableLiveData<ArrayList<Address>> addressList = addressViewModel.getAddresses();
        addressList.observe(this, list ->{
            if(list != null){
                Toast.makeText(getContext(), "Updated address list", Toast.LENGTH_SHORT).show();
                addressListAdapter = new AddressListAdapter(getContext(), list);
                setAdapter();
            }
        });

        return root;
    }

    private void setListeners(){
        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnAdd.setOnClickListener(this::onClick);
    }

    private void setupRV(){
        recyclerView = binding.addressList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapter(){
        recyclerView.setAdapter(addressListAdapter);
        addressListAdapter.setClickListener(this);
    }

    @Override
    public void onDeleteClick(String address) {
        addressViewModel.deleteWarning(address);
    }

    @Override
    public void onEditClick(View view, Address data) {
        Toast.makeText(getActivity(), "EDIT", Toast.LENGTH_SHORT).show();
        addressViewModel.editAddress(view,data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add: addressViewModel.addAddress(); break;
            case R.id.btn_return:
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                binding.btnAdd.setVisibility(View.GONE);
                getParentFragmentManager().beginTransaction().replace(R.id.address, new UserFragment()).commit();
            break;
            default:
            break;
        }
    }
}