package com.pis.buy2gether.usecases.home.favoriteList;

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
import com.pis.buy2gether.databinding.FragmentFavoriteListBinding;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteListAdapter.ItemClickListener {

    private FavoriteViewModel favoriteViewModel;
    private FavoriteListAdapter favoriteListAdapter;
    private FragmentFavoriteListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<String> items = new ArrayList<>();
        items.add("iPad");
        items.add("iPod");
        items.add("Mac");
        items.add("PlayStation");
        items.add("Xbox");
        items.add("LG TV");
        items.add("Despacito");
        items.add("Golfo");
        items.add("Juanjo");
        items.add("Tumama");
        items.add("This is a sample long list where we try to maximize the amount of words we can fit but it is just a test");
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
        RecyclerView recyclerView = binding.favoriteList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteListAdapter = new FavoriteListAdapter(getContext(), items);
        recyclerView.setAdapter(favoriteListAdapter);
        favoriteListAdapter.setClickListener(this);
        binding.favoriteList.setAdapter(favoriteListAdapter);

        favoriteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // data to populate the RecyclerView with
            }

        });
        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + favoriteListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}