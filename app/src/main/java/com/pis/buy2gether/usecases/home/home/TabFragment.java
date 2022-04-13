package com.pis.buy2gether.usecases.home.home;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    private RecyclerView recyclerView;
    private TypeRvAdapter typeRvAdapter;
    private Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.informatica_tab_fragment, container,false);
        //return root;

        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        context = root.getContext();

        ArrayList<TypeTabModel> products = new ArrayList<>();
        products.add(new TypeTabModel(R.drawable.home_info1,"Info1"));
        products.add(new TypeTabModel(R.drawable.home_info2,"Info2"));
        products.add(new TypeTabModel(R.drawable.home_info3,"Info3"));
        products.add(new TypeTabModel(R.drawable.home_info4,"Info4"));
        products.add(new TypeTabModel(R.drawable.home_info5,"Info5"));
        products.add(new TypeTabModel(R.drawable.home_info6,"Info6"));
        products.add(new TypeTabModel(R.drawable.home_info7,"Info7"));
        products.add(new TypeTabModel(R.drawable.home_info8,"Info8"));
        products.add(new TypeTabModel(R.drawable.home_info9,"Info9"));
        products.add(new TypeTabModel(R.drawable.home_info10,"Info10"));
        products.add(new TypeTabModel(R.drawable.home_info11,"Info11"));
        products.add(new TypeTabModel(R.drawable.home_info12,"Info12"));
        products.add(new TypeTabModel(R.drawable.home_info13,"Info13"));
        products.add(new TypeTabModel(R.drawable.home_info14,"Info14"));
        products.add(new TypeTabModel(R.drawable.home_info15,"Info15"));
        products.add(new TypeTabModel(R.drawable.home_info16,"Info16"));
        products.add(new TypeTabModel(R.drawable.home_info17,"Info17"));
        products.add(new TypeTabModel(R.drawable.home_info18,"Info18"));
        products.add(new TypeTabModel(R.drawable.home_info19,"Info19"));
        products.add(new TypeTabModel(R.drawable.home_info20,"Info20"));
        //products.add(new TypeTabModel(R.drawable.home_info21,"Info21"));

        recyclerView = root.findViewById(R.id.rv_tab3);
        typeRvAdapter = new TypeRvAdapter(products, getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(typeRvAdapter);

        return root;
    }
}
