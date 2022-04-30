package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;


public class TotsFragment extends Fragment implements LifecycleOwner{

    private int state;
    private TotsListAdapter adapter;
    private TotsViewModel viewModel;
    private RecyclerView recyclerView;

    Observer<ArrayList<Grup>> grupListUpdateObserver = new Observer<ArrayList<Grup>>() {
        @Override
        public void onChanged(ArrayList<Grup> userArrayList) {
            adapter.updateUserList(userArrayList);
        }
    };


    public TotsFragment(int state) {
        this.state = state;
    }
        // Required empty public constructor
    public TotsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tots, container, false);
        viewModel = new TotsViewModel();
        //viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), grupListUpdateObserver);
        recyclerView = view.findViewById(R.id.historial_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TotsListAdapter(viewModel.getGrupList(),getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }
}