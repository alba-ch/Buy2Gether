package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    ImageButton btn_return;
    ImageButton btn_change_psw;
    ImageButton btn_edit_pfp;
    EditText edit_username;
    EditText edit_city;
    TextView signout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        btn_return = view.findViewById(R.id.btn_return);
        btn_change_psw = view.findViewById(R.id.btn_change_psw);
        btn_edit_pfp = view.findViewById(R.id.btn_edit_pfp);
        edit_username = view.findViewById(R.id.edit_username);
        edit_city = view.findViewById(R.id.edit_city);
        signout = view.findViewById(R.id.text_signout);

        signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Eliminem dades guardades localment de l'usuari (email i contrasenya)
                SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                prefs.edit().clear().apply();
                // Tanquem sessió
                FirebaseAuth.getInstance().signOut();
                // Mostrem missatge d'èxit per confirmar que s'ha tancat sessió
                showSuccessAlert();
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"RETURN",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment a user options */
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.settings, new UserFragment());
                fragmentTransaction.commit();
            }
        });

        btn_edit_pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(R.layout.alert_dialog_avatar);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
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
}