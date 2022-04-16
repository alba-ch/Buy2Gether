package com.pis.buy2gether.usecases.home.user.address;

import android.content.ClipData;
import android.graphics.Color;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentAddressBinding;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddressFragment extends Fragment implements AddressListAdapter.ItemClickListener {

    private AddressViewModel addressViewModel;
    private AddressListAdapter addressListAdapter;
    private FragmentAddressBinding binding;
    private ArrayList<ClipData.Item> list;

    ImageButton btn_return, btn_save, btn_cancel;
    Button btn_add;
    EditText popup_address, popup_phone, popup_postalcode, popup_name;
    TextView popup_num;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        binding = FragmentAddressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_address,container,false);
        btn_return = view.findViewById(R.id.btn_return);
        btn_add = view.findViewById(R.id.btn_add);

        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnAdd.setOnClickListener(this::onClick);

        ArrayList<String> items = new ArrayList<>();
        items.add("iPad");
        items.add("iPod");
        items.add("Mac");
        items.add("PlayStation");
        items.add("Xbox");
        items.add("LG TV");
        items.add("Despacito");
        items.add("Golfo");

        // set up the RecyclerView
        RecyclerView recyclerView = binding.addressList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addressListAdapter = new AddressListAdapter(getContext(), items);
        recyclerView.setAdapter(addressListAdapter);
        addressListAdapter.setClickListener(this);
        binding.addressList.setAdapter(addressListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                addressListAdapter.swipe((AddressListAdapter.ViewHolder) viewHolder);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        addressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // data to populate the RecyclerView with
            }

        });

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + addressListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        boolean process = view.getId() != R.id.shareDummy;
        switch (view.getId()) {
            case R.id.btn_add: addAddress(); break;
            case R.id.btn_return:
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.address, new UserFragment()).commit();
            break;
            default:
                break;
        }
    }

    private void addAddress(){
        AlertDialog dialog = setupAddressPopup();
        btn_save.setOnClickListener(v -> {saveAddress();});
        btn_cancel.setOnClickListener(v -> {dialog.dismiss();});
    }

    private AlertDialog setupAddressPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View addressPopupView = getLayoutInflater().inflate(R.layout.alert_dialog_address,null);
        popup_num = addressPopupView.findViewById(R.id.popup_txt_num);
        popup_num.setText("Adreça "+1);

        popup_address = addressPopupView.findViewById(R.id.popup_address);
        popup_name = addressPopupView.findViewById(R.id.popup_name);
        popup_phone = addressPopupView.findViewById(R.id.popup_phone);
        popup_postalcode = addressPopupView.findViewById(R.id.popup_postalcode);

        btn_save = addressPopupView.findViewById(R.id.btn_add_address_dialog);
        btn_cancel = addressPopupView.findViewById(R.id.btn_cancel_address_dialog);

        builder.setView(addressPopupView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    private void saveAddress(){
        addressViewModel.saveAddressDB(popup_name.getText().toString(),popup_address.getText().toString(),popup_phone.getText().toString(),popup_postalcode.getText().toString());
    }
}