package com.pis.buy2gether.usecases.home.favoriteList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Favorite;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteListAdapter.ItemClickListener, View.OnClickListener {

    private FavoriteViewModel favoriteViewModel;
    private FavoriteListAdapter favoriteListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);
        View view = inflater.inflate(R.layout.fragment_tots, container, false);

        MutableLiveData<ArrayList<Favorite>> data = favoriteViewModel.getFavoriteList();
        data.observeForever(list -> {
            if (list != null)
                favoriteListAdapter.updateList(list);
        });

        setList(view);
        return view;
    }

    private void setList(View view) {
        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.favorite_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteListAdapter = new FavoriteListAdapter(getContext());
        recyclerView.setAdapter(favoriteListAdapter);
        favoriteListAdapter.setClickListener(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                switch (swipeDir) {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        favoriteViewModel.delete(favoriteListAdapter.getItem(viewHolder.getAdapterPosition()).getId());
                        break;
                    default:
                        break;
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        favoriteListAdapter.setList(favoriteViewModel.getFavoriteList());
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + favoriteListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        int offset = 0, size = favoriteListAdapter.getItemCount();
        if (view.getId() == getActivity().findViewById(R.id.submitButton).getId()) {
            for (int i = 0; i < size; i++) {
                Favorite f = favoriteListAdapter.getItem(i - offset);
                if (f.isChecked()) {
                    favoriteViewModel.delete(f.getId());
                    offset++;
                }
            }
        }
    }
}