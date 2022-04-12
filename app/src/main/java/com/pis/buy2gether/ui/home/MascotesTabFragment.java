package com.pis.buy2gether.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.MascotaTabFragmentBinding;
import com.pis.buy2gether.databinding.ModaTabFragmentBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MascotesTabFragment extends Fragment {

    MascotaTabFragmentBinding binding;
    private RecyclerView recyclerView;
    private TypeRvAdapter typeRvAdapter;
    private Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.mascota_tab_fragment, container,false);
        //return root;
        binding = MascotaTabFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();

        ArrayList<TypeTabModel> products = new ArrayList<>();
        products.add(new TypeTabModel(R.drawable.home_ma1,"mascota1"));
        products.add(new TypeTabModel(R.drawable.home_ma2,"mascota2"));
        products.add(new TypeTabModel(R.drawable.home_ma3,"mascota3"));
        products.add(new TypeTabModel(R.drawable.home_ma4,"mascota4"));
        products.add(new TypeTabModel(R.drawable.home_ma5,"mascota5"));
        products.add(new TypeTabModel(R.drawable.home_ma6,"mascota6"));
        products.add(new TypeTabModel(R.drawable.home_ma7,"mascota7"));
        products.add(new TypeTabModel(R.drawable.home_ma8,"mascota8"));
        products.add(new TypeTabModel(R.drawable.home_ma9,"mascota9"));
        products.add(new TypeTabModel(R.drawable.home_ma10,"mascota10"));
        products.add(new TypeTabModel(R.drawable.home_ma11,"mascota11"));
        products.add(new TypeTabModel(R.drawable.home_ma12,"mascota12"));
        products.add(new TypeTabModel(R.drawable.home_ma13,"mascota13"));
        products.add(new TypeTabModel(R.drawable.home_ma14,"mascota14"));
        products.add(new TypeTabModel(R.drawable.home_ma15,"mascota15"));
        products.add(new TypeTabModel(R.drawable.home_ma16,"mascota16"));
        products.add(new TypeTabModel(R.drawable.home_ma17,"mascota17"));
        products.add(new TypeTabModel(R.drawable.home_ma18,"mascota18"));
        products.add(new TypeTabModel(R.drawable.home_ma19,"mascota19"));
        products.add(new TypeTabModel(R.drawable.home_ma20,"mascota20"));
        products.add(new TypeTabModel(R.drawable.home_ma21,"mascota21"));
        products.add(new TypeTabModel(R.drawable.home_ma22,"mascota22"));

        recyclerView = binding.rvTab4;
        typeRvAdapter = new TypeRvAdapter(products, getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(typeRvAdapter);

        return root;
    }
}
