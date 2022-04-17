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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentAddressBinding;
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

        // set up list of addresses from current user
        recyclerView = binding.addressList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setList();

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked smth " + position, Toast.LENGTH_SHORT).show();
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

    private void setList(){
        ArrayList<Map> items = new ArrayList<>();
        Task task = addressViewModel.getAddresses().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    items.add(documentSnapshot.getData());
                    Toast.makeText(getContext(), "DATA: " + documentSnapshot.getData().get("Address name") + items.size(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                addressListAdapter = new AddressListAdapter(getContext(), items);
                recyclerView.setAdapter(addressListAdapter);
                binding.addressList.setAdapter(addressListAdapter);
            }
        });
    }
}