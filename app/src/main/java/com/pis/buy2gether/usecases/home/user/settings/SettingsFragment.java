package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentSettingsBinding;
import com.pis.buy2gether.databinding.FragmentUserBinding;
import com.pis.buy2gether.usecases.home.user.UserFragment;

import java.util.Objects;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    Button btn_check_image,btn_change_image,btn_cancel_avatar_dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new SettingsViewModel(getContext());

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnEditPfp.setOnClickListener(this::onClick);
        binding.btnChangePsw.setOnClickListener(this::onClick);
        binding.textSignout.setOnClickListener(this::onClick);

        return root;
    }


    /**
     * creem l'alert dialog per configurar la imatge de l'avatar
     * @return
     */
    private AlertDialog setupAvatarConfigurationPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View avatarPopupView = getLayoutInflater().inflate(R.layout.alert_dialog_avatar,null);

        btn_check_image = avatarPopupView.findViewById(R.id.btn_check_image);
        btn_change_image = avatarPopupView.findViewById(R.id.btn_change_image);
        btn_cancel_avatar_dialog = avatarPopupView.findViewById(R.id.btn_cancel_avatar_dialog);

        builder.setView(avatarPopupView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    private void check_image(){

    }

    private void change_image(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showSuccessAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Sessió tancada amb èxit");
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_signout:
                // Eliminem dades guardades localment de l'usuari (email i contrasenya)
                SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                prefs.edit().clear().apply();

                viewModel.clearSession();
                // Tanquem sessió
                FirebaseAuth.getInstance().signOut();
                // Mostrem missatge d'èxit per confirmar que s'ha tancat sessió
                showSuccessAlert();
                break;
            case R.id.btn_return:
                Toast.makeText(getActivity(),"RETURN",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment a user options */
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.settings, new UserFragment());
                fragmentTransaction.commit();
                break;
            case R.id.btn_edit_pfp:
                AlertDialog avatar_alert_dialog = setupAvatarConfigurationPopup();
                btn_check_image.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                btn_change_image.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                btn_cancel_avatar_dialog.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                break;
            case R.id.btn_change_psw:
                Toast.makeText(getActivity(), "CANVI CONTRASENYA", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}