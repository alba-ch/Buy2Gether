package com.pis.buy2gether.usecases.home.user.comanda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pis.buy2gether.R;
import com.pis.buy2gether.usecases.common.adaptadoresUniversales.SeccionesAdapter;
import com.pis.buy2gether.usecases.home.user.comanda.pestanyes_fragments.TotsFragment;
import com.pis.buy2gether.usecases.home.user.UserFragment;

/**
 * A fragment representing a list of Items.
 */
public class HistorialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton back;

    private View view;
    private TabLayout menu;
    private ViewPager viewpager;
    private SeccionesAdapter viewPageAdapter;

    public HistorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialFragment newInstance(String param1, String param2) {
        HistorialFragment fragment = new HistorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("onCreateView");
        view = inflater.inflate(R.layout.fragment_historial, container, false);


        back = view.findViewById(R.id.back);
        menu = view.findViewById(R.id.menus);
        viewpager = view.findViewById(R.id.historial_menus);
        viewPageAdapter = new SeccionesAdapter(getChildFragmentManager());
        viewPageAdapter.addFragment(new TotsFragment(), "Tots");
        viewPageAdapter.addFragment(new TotsFragment(), "En procès");
        viewPageAdapter.addFragment(new TotsFragment(), "Valorats");
        menu.setScrollPosition(0,0f,true);
        viewpager.setCurrentItem(0);
        viewpager.setAdapter(viewPageAdapter);
        menu.setupWithViewPager(viewpager);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.useropt, new UserFragment()).commit();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}