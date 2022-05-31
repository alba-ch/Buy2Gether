package com.pis.buy2gether.usecases.home.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentSettingsBinding;
import com.pis.buy2gether.databinding.FragmentUserBinding;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.home.user.address.AddressFragment;
import com.pis.buy2gether.usecases.home.user.friends.FriendsFragment;
import com.pis.buy2gether.usecases.home.user.help.HelpFragment;
import com.pis.buy2gether.usecases.home.user.comanda.HistorialFragment;
import com.pis.buy2gether.usecases.home.user.settings.SettingsFragment;
import com.pis.buy2gether.usecases.onboarding.log_in.LoginActivity;
import com.pis.buy2gether.usecases.onboarding.sign_in.RegisterActivity;

public class UserFragment extends Fragment implements View.OnClickListener {

    private FragmentUserBinding binding;
    private UserViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        viewModel = new UserViewModel(getContext());

        View root = binding.getRoot();

        if(viewModel.checkGuest()){
            binding.btnComandes.setImageResource(R.drawable.btn_comandes);
            setOnClickListeners();
        }else{
            binding.btnComandes.setImageResource(R.drawable.btn_reg);
            guestSetUp();
        }

        viewModel.setup(root);

        return root;
    }

    private void guestSetUp() {
        binding.btnComandes.setOnClickListener(
                act ->{
                    Intent i = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(i);
                    this.getActivity().finish();
                }
        );
        binding.btnAdreces.setVisibility(View.INVISIBLE);
        binding.btnAjuda.setVisibility(View.INVISIBLE);
        binding.btnAmics.setVisibility(View.INVISIBLE);
        binding.btnSettings.setVisibility(View.INVISIBLE);

    }

    private void setOnClickListeners(){
        binding.btnComandes.setOnClickListener(this::onClick);
        binding.btnAdreces.setOnClickListener(this::onClick);
        binding.btnAjuda.setOnClickListener(this::onClick);
        binding.btnAmics.setOnClickListener(this::onClick);
        binding.btnSettings.setOnClickListener(this::onClick);
        binding.btnLan.setOnClickListener(this::onClick);
        binding.btnLan.setEnabled(false);
        binding.btnLan.setVisibility(View.INVISIBLE);
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
}