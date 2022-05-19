package com.pis.buy2gether.usecases.home.user.address;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.data.AddressData;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.Notificacions;
import com.pis.buy2gether.model.session.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressViewModel extends ViewModel {

    /* Mutable Live Data for Address List autoupdate */
    public MutableLiveData<ArrayList<Address>> addressList;

    /* Context and layout inflater*/
    Context context;
    LayoutInflater inflater;

    /* Alert dialog widgets */
    EditText popup_address, popup_phone, popup_postalcode, popup_name;
    ImageButton btn_save, btn_cancel;
    TextView popup_num;

    public AddressViewModel(Context context, LayoutInflater inflater) {
        this.addressList = new MutableLiveData<>();
        this.context = context;
        this.inflater = inflater;

        init();
    }

    private void init(){
        addressList = AddressData.INSTANCE.getAddressList(getUser());
    }

    void saveAddress(){ AddressData.INSTANCE.saveAddress(getUser(),popup_name.getText().toString(),popup_address.getText().toString(),popup_phone.getText().toString(),popup_postalcode.getText().toString()); }

    void deleteAddress(String nom){
        AddressData.INSTANCE.deleteAddress(getUser(), nom);
    }

    private String getUser(){
        String id = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return id;
    }

    public void addAddress(){
        AlertDialog dialog = setupAddressPopup();
        btn_save.setOnClickListener(v -> {saveAddress();});
        btn_cancel.setOnClickListener(v -> {dialog.dismiss();});
    }

    public void editAddress(View view, Address data){
        AlertDialog dialog = setupEditAddressPopup(view, data);
        btn_save.setOnClickListener(v -> {saveAddress();});
        btn_cancel.setOnClickListener(v -> {dialog.dismiss();});
    }

    public void deleteWarning(String nom){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Segur que vols eliminar aquesta adreça?");
        builder.setNegativeButton("Cancelar",null);
        builder.setPositiveButton("Acceptar",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                deleteAddress(nom);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private AlertDialog setupAddressPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View addressPopupView = inflater.inflate(R.layout.alert_dialog_address,null);
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

    public AlertDialog setupEditAddressPopup(View view, Address data){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View addressPopupView = inflater.inflate(R.layout.alert_dialog_address,null);

        popup_num = addressPopupView.findViewById(R.id.popup_txt_num);
        popup_num.setText("Adreça "+1);

        popup_address = addressPopupView.findViewById(R.id.popup_address);
        popup_address.setText(data.getAddress());

        popup_name = addressPopupView.findViewById(R.id.popup_name);
        popup_name.setText(data.getName());
        popup_name.setEnabled(false);
        popup_name.setTypeface(null, Typeface.BOLD_ITALIC);

        popup_phone = addressPopupView.findViewById(R.id.popup_phone);
        popup_phone.setText(data.getTelephone());

        popup_postalcode = addressPopupView.findViewById(R.id.popup_postalcode);
        popup_postalcode.setText(data.getZip());

        btn_save = addressPopupView.findViewById(R.id.btn_add_address_dialog);
        btn_cancel = addressPopupView.findViewById(R.id.btn_cancel_address_dialog);

        builder.setView(addressPopupView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    public MutableLiveData<ArrayList<Address>> getAddresses(){
        return addressList;
    }
}