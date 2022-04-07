package com.pis.buy2gether.ui.group.creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentGroupCreationBinding;
import com.pis.buy2gether.databinding.FragmentShareGroupBinding;
import com.pis.buy2gether.ui.group.share.FriendListAdapter;
import com.pis.buy2gether.ui.notifications.NotificationsListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class GroupCreationFragment extends Fragment implements View.OnClickListener {

    private FriendListAdapter friendListAdapter;
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

        binding.groupPopup.otherUsers.setClickable(true);
        binding.groupPopup.otherUsers.setFocusable(true);
        binding.groupPopup.otherUsers.setOnClickListener(this);

        ArrayList<String> items = new ArrayList<>();
        items.add("Horse");
        items.add("Cow");
        items.add("Camel");
        items.add("Sheep");
        items.add("Chen");
        items.add("Tula");
        items.add("Chica");
        items.add("Golfo");
        items.add("Juanjo");
        items.add("Tumama");
        items.add("Goat");
        items.add("Goat");
        items.add("Goat");
        items.add("Goat");
        items.add("Goat");
        items.add("Goat");
        items.add("Goat");
        items.add("Sheep");
        items.add("Sheep");
        items.add("Goat");
        items.add("Goat");


        // set up the RecyclerView
        RecyclerView recyclerView = binding.groupPopup.friendList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendListAdapter = new FriendListAdapter(getContext(), items);
        recyclerView.setAdapter(friendListAdapter);
        binding.groupPopup.friendList.setAdapter(friendListAdapter);
        binding.publicButton.setOnClickListener(this);
        binding.privateButton.setOnClickListener(this);
        binding.hiddenButton.setOnClickListener(this);

        binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));


        binding.groupPopup.shareDummy.setOnClickListener(this);
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
        boolean process = view.getId() != R.id.shareDummy;
        switch (view.getId()){
            case R.id.privateButton:
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                process = false;
                break;
            case R.id.hiddenButton:
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                process = false;
                break;
            case R.id.publicButton:
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                process = false;
                break;
            default:
                break;
        }
        if(process) {
            if (view.getId() == binding.getRoot().getId()) {
                binding.groupPopup.otherUsers.setClickable(false);
                binding.groupPopup.otherUsers.setFocusable(false);

                binding.groupPopup.sharemessages.setClickable(false);
                binding.groupPopup.sharemessages.setFocusable(false);
                binding.groupPopup.linkImage.setClickable(false);
                binding.groupPopup.linkImage.setFocusable(false);
                binding.groupPopup.linkInvitacio.setClickable(false);
                binding.groupPopup.linkInvitacio.setFocusable(false);
                binding.groupPopup.linkText.setClickable(false);
                binding.groupPopup.linkText.setFocusable(false);
                binding.groupPopup.moreinformation.setClickable(false);
                binding.groupPopup.moreinformation.setFocusable(false);
                binding.groupPopup.friendList.setFocusable(false);
                binding.groupPopup.friendList.setClickable(false);
                binding.submitButton.setVisibility(View.VISIBLE);
                binding.groupPopup.shareDummy.setClickable(false);
                binding.groupPopup.shareDummy.setFocusable(false);
                binding.groupPopup.getRoot().setVisibility(View.INVISIBLE);
            } else if (view.getId() == binding.groupPopup.otherUsers.getId()) {
                binding.groupPopup.linkImage.setVisibility(View.INVISIBLE);
                binding.groupPopup.linkImage.setClickable(false);
                binding.groupPopup.linkImage.setFocusable(false);
                binding.groupPopup.linkInvitacio.setVisibility(View.INVISIBLE);
                binding.groupPopup.linkInvitacio.setClickable(false);
                binding.groupPopup.linkInvitacio.setFocusable(false);
                binding.groupPopup.linkText.setVisibility(View.INVISIBLE);
                binding.groupPopup.linkText.setClickable(false);
                binding.groupPopup.linkText.setFocusable(false);
                binding.groupPopup.moreinformation.setVisibility(View.INVISIBLE);
                binding.groupPopup.moreinformation.setClickable(false);
                binding.groupPopup.moreinformation.setFocusable(false);
                binding.groupPopup.sharemessages.setVisibility(View.INVISIBLE);
                binding.groupPopup.sharemessages.setClickable(false);
                binding.groupPopup.sharemessages.setFocusable(false);
                binding.groupPopup.otherUsers.setVisibility(View.INVISIBLE);
                binding.groupPopup.otherUsers.setClickable(false);
                binding.groupPopup.otherUsers.setFocusable(false);
                binding.groupPopup.friendList.setVisibility(View.VISIBLE);
                binding.groupPopup.friendList.setFocusable(true);
                binding.groupPopup.friendList.setClickable(true);
            } else {
                binding.groupPopup.shareDummy.setClickable(true);
                binding.groupPopup.shareDummy.setFocusable(true);

                binding.groupPopup.otherUsers.setVisibility(View.VISIBLE);
                binding.groupPopup.otherUsers.setClickable(true);
                binding.groupPopup.otherUsers.setFocusable(true);

                binding.groupPopup.sharemessages.setVisibility(View.VISIBLE);
                binding.groupPopup.sharemessages.setClickable(true);
                binding.groupPopup.sharemessages.setFocusable(true);
                binding.groupPopup.linkImage.setVisibility(View.VISIBLE);
                binding.groupPopup.linkImage.setClickable(true);
                binding.groupPopup.linkImage.setFocusable(true);
                binding.groupPopup.linkInvitacio.setVisibility(View.VISIBLE);
                binding.groupPopup.linkInvitacio.setClickable(true);
                binding.groupPopup.linkInvitacio.setFocusable(true);
                binding.groupPopup.linkText.setVisibility(View.VISIBLE);
                binding.groupPopup.linkText.setClickable(true);
                binding.groupPopup.linkText.setFocusable(true);
                binding.groupPopup.moreinformation.setVisibility(View.VISIBLE);
                binding.groupPopup.moreinformation.setClickable(true);
                binding.groupPopup.moreinformation.setFocusable(true);
                binding.groupPopup.friendList.setVisibility(View.INVISIBLE);
                binding.groupPopup.friendList.setFocusable(false);
                binding.groupPopup.friendList.setClickable(false);
                binding.submitButton.setVisibility(View.INVISIBLE);
                binding.groupPopup.getRoot().setVisibility(View.VISIBLE);
            }
        }
    }
}