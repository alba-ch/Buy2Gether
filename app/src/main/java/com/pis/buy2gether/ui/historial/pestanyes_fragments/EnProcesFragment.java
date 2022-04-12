package com.pis.buy2gether.ui.historial.pestanyes_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pis.buy2gether.R;
import com.pis.buy2gether.ui.historial.HistorialListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnProcesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnProcesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnProcesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnProces.
     */
    // TODO: Rename and change types and number of parameters
    public static EnProcesFragment newInstance(String param1, String param2) {
        EnProcesFragment fragment = new EnProcesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_en_proces, container, false);
        ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            lista.add("Elemento in process" + i);
        }
        RecyclerView recycler = view.findViewById(R.id.historial_list);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        HistorialListAdapter adapter = new HistorialListAdapter(lista, getActivity());
        recycler.setAdapter(adapter);
        return view;
    }
}