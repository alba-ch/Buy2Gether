package com.pis.buy2gether.ui.group.creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.pis.buy2gether.databinding.FragmentGroupCreationBinding;

public class GroupCreationFragment extends Fragment implements View.OnClickListener {

    private GroupCreationViewModel groupCreationViewModel;
    private FragmentGroupCreationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupCreationViewModel =
                new ViewModelProvider(this).get(GroupCreationViewModel.class);

        binding = FragmentGroupCreationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.submitButton.setOnClickListener(this);
        root.setClickable(true);
        root.setOnClickListener(this);
        groupCreationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        if(view.getId() == binding.getRoot().getId()){
            binding.usersLimit.setClickable(true);
            binding.usersLimit.setFocusable(true);
            binding.originalPrice.setClickable(true);
            binding.originalPrice.setFocusable(true);
            binding.submitButton.setClickable(true);
            binding.submitButton.setFocusable(true);
            binding.submitButton.setVisibility(View.VISIBLE);
            binding.groupPopup.getRoot().setVisibility(View.INVISIBLE);
        } else {
            binding.usersLimit.setClickable(false);
            binding.usersLimit.setFocusable(false);
            binding.originalPrice.setClickable(false);
            binding.originalPrice.setFocusable(false);
            binding.submitButton.setClickable(false);
            binding.submitButton.setFocusable(false);
            binding.submitButton.setVisibility(View.INVISIBLE);
            binding.groupPopup.getRoot().setVisibility(View.VISIBLE);
        }
    }
}