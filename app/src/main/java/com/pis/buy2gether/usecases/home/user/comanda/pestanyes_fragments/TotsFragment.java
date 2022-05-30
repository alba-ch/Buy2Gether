package com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.pis.buy2gether.R;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import java.util.ArrayList;
import java.util.Collection;


public class TotsFragment extends Fragment{

    private int state;
    private TotsListAdapter adapter;
    private TotsViewModel viewModel;
    private RecyclerView recyclerView;



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
        Log.i("TotsFragment", "onCreateView: ");
        MutableLiveData<ArrayList<Grup>> data = viewModel.getGrupList();
        data.observeForever(list ->{
            if (list != null)
                adapter.updateList(list);
        });
        recyclerView = view.findViewById(R.id.historial_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TotsListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

}