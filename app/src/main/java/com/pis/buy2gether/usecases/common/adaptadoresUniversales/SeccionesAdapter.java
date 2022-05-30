package com.pis.buy2gether.usecases.common.adaptadoresUniversales;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SeccionesAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> listaFragments = new ArrayList<>();
    private final ArrayList<String> listaTitulos = new ArrayList<>();

    public SeccionesAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void  addFragment(@NonNull Fragment fragment, @NonNull String titulo){
        listaFragments.add(fragment);
        listaTitulos.add(titulo);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }
}
