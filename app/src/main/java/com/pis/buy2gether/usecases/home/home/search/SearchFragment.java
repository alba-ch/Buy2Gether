package com.pis.buy2gether.usecases.home.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentSearchBinding;
import com.pis.buy2gether.usecases.home.home.TabFragment;


import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        ArrayList<String> items = new ArrayList<>();
        items.add("Horse");
        items.add("Cow");
        items.add("Camel");
        items.add("Sheep");
        items.add("Chen");
        items.add("Tula");
        items.add("Chica");
        items.add("Golfo");

        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);

        SearchView search_bar = view.findViewById(R.id.searchView);
        ListView historial_list = view.findViewById(R.id.historial_list);
        historial_list.setAdapter(searchAdapter);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* User prem enter */
                search_bar.setQuery(query,false);
                search_bar.clearFocus();
                if(items.contains(query)){
                    searchAdapter.getFilter().filter(query);
                }

                getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.viewPager, new TabFragment(query));
                fragmentTransaction.addToBackStack("home").commit();



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /* User prem una lletra qualsevol*/
                searchAdapter.getFilter().filter(newText);
                return false;
            }


        });
        return view;
    }

    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
        super.onDestroyView();
        binding = null;
    }

}