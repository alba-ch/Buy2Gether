package com.pis.buy2gether.usecases.home.paymentWall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentPaymentWallBinding;

public class PaymentWallFragment extends Fragment implements View.OnClickListener {

    private PaymentWallViewModel paymentWallViewModel;
    private FragmentPaymentWallBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentWallViewModel =
                new ViewModelProvider(this).get(PaymentWallViewModel.class);

        binding = FragmentPaymentWallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.cardLayout.setFocusable(true);
        binding.cardLayout.setClickable(true);
        binding.cardLayout.setOnClickListener(this);

        binding.paypalLayout.setFocusable(true);
        binding.paypalLayout.setClickable(true);
        binding.paypalLayout.setOnClickListener(this);

        binding.cryptoLayout.setFocusable(true);
        binding.cryptoLayout.setClickable(true);
        binding.cryptoLayout.setOnClickListener(this);
         paymentWallViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cardLayout:
                binding.card.setChecked(true);
                binding.paypal.setChecked(false);
                binding.crypto.setChecked(false);
                break;
            case R.id.paypalLayout:
                binding.card.setChecked(false);
                binding.paypal.setChecked(true);
                binding.crypto.setChecked(false);
                break;
            case R.id.cryptoLayout:
                binding.card.setChecked(false);
                binding.paypal.setChecked(false);
                binding.crypto.setChecked(true);
                break;
            default:
                binding.card.setChecked(false);
                binding.paypal.setChecked(false);
                binding.crypto.setChecked(false);
                break;
        }
    }
}