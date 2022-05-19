package com.pis.buy2gether.usecases.home.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    private RecyclerView recyclerView;
    private TypeRvAdapter typeRvAdapter;
    private Context context;
    private MutableLiveData<ArrayList<Grup>> list;

    public TabFragment(){}

    public TabFragment(Category category) {

        TypeRvViewModel TypeRvViewModel = new TypeRvViewModel();
        MutableLiveData<ArrayList<Grup>> list = TypeRvViewModel.getGrupByCategory(category);
        list.observe(this, newList -> {
            if(newList != null && newList.size() > 0){
                Log.e("TAB", "newList: " + newList.size());
                typeRvAdapter.updateList(newList);
            }
        });
    }

    public TabFragment(String search){
        TypeRvViewModel TypeRvViewModel = new TypeRvViewModel();
        MutableLiveData<ArrayList<Grup>> list = TypeRvViewModel.getGrupBySearch(search);
        list.observe(this, newList -> {
            if(newList != null){
                typeRvAdapter.updateList(newList);
            }
        });
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.informatica_tab_fragment, container,false);
        //return root;
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        context = root.getContext();
        recyclerView = root.findViewById(R.id.rv_tab3);
        typeRvAdapter = new TypeRvAdapter(new ArrayList<>(), getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(typeRvAdapter);
        return root;
    }
}
