package com.pis.buy2gether.ui.historial;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentHistorialBinding;
import com.pis.buy2gether.ui.friends.FriendsListAdapter;
import com.pis.buy2gether.ui.user.UserFragment;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class HistorialFragment extends Fragment implements HistorialListAdapter.ItemClickListener{
    private HistorialViewModel friendsViewModel;
    private HistorialListAdapter friendsListAdapter;
    private FragmentHistorialBinding binding;
    private ArrayList<ClipData.Item> list;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        friendsViewModel = new ViewModelProvider(this).get(HistorialViewModel.class);

        binding = FragmentHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_historial,container,false);




        ArrayList<String> items = new ArrayList<>();


        // set up the RecyclerView
        RecyclerView recyclerView = binding.historialList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendsListAdapter = new HistorialListAdapter(getContext(), items);
        recyclerView.setAdapter(friendsListAdapter);
        friendsListAdapter.setClickListener(this);
        binding.historialList.setAdapter(friendsListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                friendsListAdapter.swipe((FriendsListAdapter.ViewHolder) viewHolder);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        friendsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // data to populate the RecyclerView with
            }

        });

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + friendsListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void onClick(View view) {
        boolean process = view.getId() != R.id.shareDummy;
        switch (view.getId()) {
            case R.id.btn_return:
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.friends, new UserFragment()).commit();
                break;
            default:
                break;
        }
    }
}