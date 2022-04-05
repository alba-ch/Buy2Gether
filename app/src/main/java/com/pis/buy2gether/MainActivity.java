package com.pis.buy2gether;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.tabs.TabLayout;
import com.pis.buy2gether.databinding.ActivityMainBinding;
import com.pis.buy2gether.ui.home.CategoriaAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    TabLayout tabLayout_categoria;
    ViewPager viewPager_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * CONFIGURACIÓ DELS FRAGMENTS (codi de intellij)
        * */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_cart,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*
        * CONFIGURACIÓ TabLayout i ViewPager per categories
        *
        * */

        //inicialitzem els components del fragment home
        tabLayout_categoria = findViewById(R.id.tab_layout);
        viewPager_categoria = findViewById(R.id.viewPager);
        //títols dels tablayouts
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Moda"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Electrònica"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Informàtica"));
        tabLayout_categoria.addTab(tabLayout_categoria.newTab().setText("Mascotes"));
        //estiguin repartits entre ells
        tabLayout_categoria.setTabGravity(tabLayout_categoria.GRAVITY_FILL);
        //instanciem l'adaptador per viewpager de home_fragment
        final CategoriaAdapter adapter_categoria= new CategoriaAdapter(getSupportFragmentManager(),this,tabLayout_categoria.getTabCount());
        //adaptem el viewPager amb l'adaptador que acabem de crear
        viewPager_categoria.setAdapter(adapter_categoria);
        //li afegim un listener
        viewPager_categoria.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_categoria));

    }

}