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
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.usecases.home.home.TabFragment;


import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        ArrayList<Grup> items = new ArrayList<>();

        ArrayAdapter<Grup> searchAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);

        SearchView search_bar = view.findViewById(R.id.searchView);
        ListView historial_list = view.findViewById(R.id.historial_list);
        historial_list.setAdapter(searchAdapter);
        search_bar.requestFocusFromTouch();

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* User prem enter */
                    search_bar.setQuery(query, false);
                    search_bar.clearFocus();
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
    }

}