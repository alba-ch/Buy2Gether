package com.pis.buy2gether.ui.home;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.pis.buy2gether.databinding.ModaTabFragmentBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriaAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;
    private List<Fragment> mFragments;

    /**
     * constructor 1: li passem el nombre de tabs
     * @param fm
     * @param context
     * @param totalTabs
     */
    public CategoriaAdapter(@NonNull @NotNull FragmentManager fm,Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    /**
     * constructor 1: li passem la llista de fragments
     * @param fm
     * @param fragments
     */
    public CategoriaAdapter(@NonNull @NotNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments=fragments;
        fm.beginTransaction().commitAllowingStateLoss();
    }

    /**
     * retorna fragment seleccionat
     * @param position
     * @return fragment seleccionat
     */
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                ModaTabFragment modaTabFragment = new ModaTabFragment();
                return modaTabFragment;
            case 1:
                ElectronicaTabFragment electronicaTabFragment = new ElectronicaTabFragment();
                return electronicaTabFragment;
            case 2:
                InformaticaTabFragment informaticaTabFragment = new InformaticaTabFragment();
                return informaticaTabFragment;
            case 3:
                MascotesTabFragment mascotesTabFragment = new MascotesTabFragment();
                return  mascotesTabFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return totalTabs;
    }
}
