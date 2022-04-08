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
import com.pis.buy2gether.databinding.ElectronicaTabFragmentBinding;
import com.pis.buy2gether.databinding.ModaTabFragmentBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ElectronicaTabFragment extends Fragment {

    ElectronicaTabFragmentBinding binding;
    private RecyclerView recyclerView;
    private TypeRvAdapter typeRvAdapter;
    private Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ElectronicaTabFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();

        ArrayList<TypeTabModel> products = new ArrayList<>();
        products.add(new TypeTabModel(R.drawable.home_comp1,"Comp1"));
        products.add(new TypeTabModel(R.drawable.home_comp2,"Comp2"));
        products.add(new TypeTabModel(R.drawable.home_comp3,"Comp3"));
        products.add(new TypeTabModel(R.drawable.home_comp4,"Comp4"));
        products.add(new TypeTabModel(R.drawable.home_comp5,"Comp5"));
        products.add(new TypeTabModel(R.drawable.home_comp6,"Comp6"));
        products.add(new TypeTabModel(R.drawable.home_comp7,"Comp7"));
        products.add(new TypeTabModel(R.drawable.home_comp8,"Comp8"));
        products.add(new TypeTabModel(R.drawable.home_comp9,"Comp9"));
        products.add(new TypeTabModel(R.drawable.home_comp10,"Comp10"));
        products.add(new TypeTabModel(R.drawable.home_comp11,"Comp11"));
        products.add(new TypeTabModel(R.drawable.home_comp12,"Comp12"));
        products.add(new TypeTabModel(R.drawable.home_comp13,"Comp13"));
        products.add(new TypeTabModel(R.drawable.home_comp14,"Comp14"));
        products.add(new TypeTabModel(R.drawable.home_comp15,"Comp15"));
        products.add(new TypeTabModel(R.drawable.home_comp16,"Comp16"));
        products.add(new TypeTabModel(R.drawable.home_comp17,"Comp17"));
        products.add(new TypeTabModel(R.drawable.home_comp18,"Comp18"));
        products.add(new TypeTabModel(R.drawable.home_comp19,"Comp19"));

        recyclerView = binding.rvTab2;
        typeRvAdapter = new TypeRvAdapter(products);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(typeRvAdapter);


        return root;
    }
}
