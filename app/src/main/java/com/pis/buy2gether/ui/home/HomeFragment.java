package com.pis.buy2gether.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentHomeBinding;
import com.pis.buy2gether.ui.adaptadoresUniversales.SeccionesAdapter;
import com.pis.buy2gether.ui.help.HelpFragment;
import com.pis.buy2gether.ui.home.search.SearchFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SeccionesAdapter adapter_categoria;
    private TabLayout tabLayout_categoria;
    private ViewPager viewPager_categoria;
    private EditText search_bar;
    //private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_home, container, false);;


        //inicialitzem els components del fragment home
        tabLayout_categoria = root.findViewById(R.id.tab_layout);
        viewPager_categoria = root.findViewById(R.id.viewPager);
        search_bar = root.findViewById(R.id.search_bar);

        //instanciem l'adaptador per viewpager de home_fragment

        adapter_categoria= new SeccionesAdapter(getChildFragmentManager());
        // Solución temporal para que funcione el viewpager
        adapter_categoria.addFragment(new ModaTabFragment(), "Moda");
        adapter_categoria.addFragment(new ElectronicaTabFragment(), "Electrònica");
        adapter_categoria.addFragment(new InformaticaTabFragment(), "Informàtica");
        adapter_categoria.addFragment(new MascotesTabFragment(), "Mascotes");


        //adaptem el viewPager amb l'adaptador que acabem de crear
        viewPager_categoria.setAdapter(adapter_categoria);
        tabLayout_categoria.setupWithViewPager(viewPager_categoria);


        /*
        * homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        * */
        //afegim les dades de homevViewModel a la llista d'observed, si hi ha algun canvi es mostrara el missatge per pantalla
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"CERCA",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment al d'ajuda */
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, new SearchFragment());
                fragmentTransaction.addToBackStack("home").commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}