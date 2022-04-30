package com.pis.buy2gether.usecases.home.user.comanda;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.common.adaptadoresUniversales.SeccionesAdapter;
import com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments.TotsFragment;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments.TotsViewModel;

import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 */
public class HistorialFragment extends Fragment{

    private TotsViewModel viewModel;
    private HashMap<String,TotsFragment> fragments;
    private ImageButton back;
    private View view;
    private TabLayout menu;
    private ViewPager viewpager;
    private SeccionesAdapter viewPageAdapter;


    public HistorialFragment() {
        // Required empty public constructor
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_historial, container, false);


        back = view.findViewById(R.id.back);
        menu = view.findViewById(R.id.menus);
        viewpager = view.findViewById(R.id.historial_menus);
        viewPageAdapter = new SeccionesAdapter(getChildFragmentManager());

        fragments = new HashMap<>();
        fragments.put("Tots", new TotsFragment(-1));
        fragments.put("En procès", new TotsFragment(-1));
        fragments.put("Valorats", new TotsFragment(-1));

        viewPageAdapter.addFragment(fragments.get("Tots"), "Tots");
        viewPageAdapter.addFragment(fragments.get("En procès"), "En procès");
        viewPageAdapter.addFragment(fragments.get("Valorats"), "Valorats");

        menu.setScrollPosition(0,0f,true);

        viewpager.setCurrentItem(0);
        viewpager.setAdapter(viewPageAdapter);
        menu.setupWithViewPager(viewpager);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.useropt, new UserFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }






}