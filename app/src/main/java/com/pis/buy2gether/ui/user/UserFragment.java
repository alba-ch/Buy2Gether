package com.pis.buy2gether.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentUserBinding;
import com.pis.buy2gether.ui.address.AddressFragment;
import com.pis.buy2gether.ui.friends.FriendsFragment;
import com.pis.buy2gether.ui.help.HelpFragment;
import com.pis.buy2gether.ui.historial.HistorialFragment;
import com.pis.buy2gether.ui.settings.SettingsFragment;

import java.util.Objects;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private FragmentUserBinding binding;

    ImageButton btn_comandes;
    ImageButton btn_adreces;
    ImageButton btn_historial;
    ImageButton btn_ajuda;
    ImageButton btn_amics;
    ImageButton btn_settings;
    ImageButton btn_lan;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
            CODI INTELLIJ

        * userViewModel = new ViewModelProvider(this).get(HelpViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
        * */

        View view = inflater.inflate(R.layout.fragment_user,container,false);
        btn_comandes = view.findViewById(R.id.btn_comandes);
        btn_adreces = view.findViewById(R.id.btn_adreces);
        btn_ajuda = view.findViewById(R.id.btn_ajuda);
        btn_amics = view.findViewById(R.id.btn_amics);
        btn_historial = view.findViewById(R.id.btn_historial);
        btn_settings = view.findViewById(R.id.btn_settings);
        btn_lan = view.findViewById(R.id.btn_lan);


        /**
         * codi repetitiu -> crear classe interna
         */
        btn_comandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"COMANDES",Toast.LENGTH_SHORT).show();
            }
        });

        btn_adreces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"ADRECES",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al d'adreces */
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.useropt, new AddressFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
            }
        });

        btn_ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"AJUDA",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al d'ajuda */
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.useropt, new HelpFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
            }
        });

        btn_amics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"AMICS",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al que conté la llista d'amics */
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.useropt, new FriendsFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
            }
        });

        btn_historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"HISTORIAL",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al que conté la llista d'amics */

                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.useropt, new HistorialFragment());
                fragmentTransaction.addToBackStack("user-options").commit();

            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"SETTINGS",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al de configuracions */
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.useropt, new SettingsFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
            }
        });

        btn_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"LANGUAGE",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}