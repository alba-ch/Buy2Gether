package com.pis.buy2gether.usecases.home.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentUserBinding;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.home.user.address.AddressFragment;
import com.pis.buy2gether.usecases.home.user.friends.FriendsFragment;
import com.pis.buy2gether.usecases.home.user.help.HelpFragment;
import com.pis.buy2gether.usecases.home.user.comanda.HistorialFragment;
import com.pis.buy2gether.usecases.home.user.settings.SettingsFragment;

public class UserFragment extends Fragment implements View.OnClickListener {

    //private UserViewModel userViewModel;
    //private FragmentUserBinding binding;

    ImageButton btn_comandes;
    ImageButton btn_adreces;
    ImageButton btn_ajuda;
    ImageButton btn_amics;
    ImageButton btn_settings;
    ImageButton btn_lan;

    TextView username;
    TextView description;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);

        setup(view);


        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        switch(view.getId()){

            case R.id.btn_comandes:
                fragmentTransaction.replace(R.id.useropt, new HistorialFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
                break;
            case R.id.btn_adreces:
                //change to address Fragment
                fragmentTransaction.replace(R.id.useropt, new AddressFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
                break;
            case R.id.btn_ajuda:
                //change to help Fragment
                fragmentTransaction.replace(R.id.useropt, new HelpFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
                break;
            case R.id.btn_amics:
                //change to friends list Fragment
                fragmentTransaction.replace(R.id.useropt, new FriendsFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
                break;
            case R.id.btn_settings:
                //change to settings Fragment
                fragmentTransaction.replace(R.id.useropt, new SettingsFragment());
                fragmentTransaction.addToBackStack("user-options").commit();
                break;
            case R.id.btn_lan:
                Toast.makeText(getActivity(),"LANGUAGE",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    private void setup(View view){
        btn_comandes = view.findViewById(R.id.btn_comandes);
        btn_adreces = view.findViewById(R.id.btn_adreces);
        btn_ajuda = view.findViewById(R.id.btn_ajuda);
        btn_amics = view.findViewById(R.id.btn_amics);
        btn_settings = view.findViewById(R.id.btn_settings);
        btn_lan = view.findViewById(R.id.btn_lan);

        btn_comandes.setOnClickListener(this);
        btn_adreces.setOnClickListener(this);
        btn_ajuda.setOnClickListener(this);
        btn_amics.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_lan.setOnClickListener(this);

        setupUserInfo(view);
    }

    private void setupUserInfo(View view){
        username = view.findViewById(R.id.txt_user);
        description = view.findViewById(R.id.txt_desc);

        String provider = Session.INSTANCE.getDataSession(getContext(),"provider");
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(ProviderType.valueOf(provider) != ProviderType.GUEST) {
            Session.INSTANCE.getUserByID(currentUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    username.setText(documentSnapshot.get("username").toString());
                    description.setText(documentSnapshot.get("email").toString());
                }
            });
        }
    }


}