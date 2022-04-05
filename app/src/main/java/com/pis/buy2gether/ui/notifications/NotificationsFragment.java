package com.pis.buy2gether.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements NotificationsListAdapter.ItemClickListener {

    private NotificationsViewModel notificationsViewModel;
    private NotificationsListAdapter notificationsListAdapter;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        RecyclerView recyclerView = binding.notificationsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsListAdapter = new NotificationsListAdapter(getContext(), items);
        recyclerView.setAdapter(notificationsListAdapter);
        notificationsListAdapter.setClickListener(this);
        binding.notificationsList.setAdapter(notificationsListAdapter);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // data to populate the RecyclerView with
            }

        });
        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + notificationsListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}