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
import com.pis.buy2gether.databinding.FragmentHomeBinding;
import com.pis.buy2gether.databinding.ModaTabFragmentBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ModaTabFragment extends Fragment {

    ModaTabFragmentBinding binding;
    private RecyclerView recyclerView;
    private TypeRvAdapter typeRvAdapter;
    private Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.moda_tab_fragment, container,false);
        //return root;
        binding = ModaTabFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();

        ArrayList<TypeTabModel> products = new ArrayList<>();
        products.add(new TypeTabModel(R.drawable.home_bolsas,"Bolsas"));
        products.add(new TypeTabModel(R.drawable.home_dress,"Dress"));
        products.add(new TypeTabModel(R.drawable.home_jacket1,"jacket1"));
        products.add(new TypeTabModel(R.drawable.home_jacket2,"jacket2"));
        products.add(new TypeTabModel(R.drawable.home_jacket3,"jacket3"));
        products.add(new TypeTabModel(R.drawable.home_pants1,"pants1"));
        products.add(new TypeTabModel(R.drawable.home_pants2,"pants2"));
        products.add(new TypeTabModel(R.drawable.home_pants3,"pants3"));
        products.add(new TypeTabModel(R.drawable.home_pants4,"pants4"));
        products.add(new TypeTabModel(R.drawable.home_shoes1,"shoes1"));
        products.add(new TypeTabModel(R.drawable.home_shoes2,"shoes2"));
        products.add(new TypeTabModel(R.drawable.home_shoes3,"shoes3"));
        products.add(new TypeTabModel(R.drawable.home_socks1,"socks1"));
        products.add(new TypeTabModel(R.drawable.home_socks2,"socks2"));
        products.add(new TypeTabModel(R.drawable.home_tshirt1,"tshirt1"));
        products.add(new TypeTabModel(R.drawable.home_tshirt2,"tshirt2"));
        products.add(new TypeTabModel(R.drawable.home_tshirt3,"tshirt3"));

        recyclerView = binding.rvModa;
        typeRvAdapter = new TypeRvAdapter(products);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(typeRvAdapter);

        return root;
    }
}
