package com.pis.buy2gether.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TabLayout tabLayout_categoria;
    private ViewPager viewPager_categoria;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        //inicialitzem els components del fragment home
        tabLayout_categoria = binding.tabLayout;
        viewPager_categoria = binding.viewPager;
        //títols dels tablayouts
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Moda"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Electrònica"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Informàtica"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Mascotes"));
        //estiguin repartits entre ells
        tabLayout_categoria.setTabGravity(tabLayout_categoria.GRAVITY_FILL);
        //instanciem l'adaptador per viewpager de home_fragment
        final CategoriaAdapter adapter_categoria= new CategoriaAdapter(getParentFragmentManager(),getParentFragment().getContext(),tabLayout_categoria.getTabCount());
        //adaptem el viewPager amb l'adaptador que acabem de crear
        viewPager_categoria.setAdapter(adapter_categoria);
        //li afegim un listener
        viewPager_categoria.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_categoria));

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}