package pizzoto.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_fragment,
                R.id.menu_fragment,
                R.id.carrito_fragment,
               // R.id.custon_fragment,
                R.id.pedidos_fragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void onBackPressed() {

        int currentFragmentId = navController.getCurrentDestination().getId();
        int homeFragmentId = R.id.home_fragment;
        int productoDescriptionFragment = R.id.producto_description;
        int menu_fragment = R.id.menu_fragment;

        if (currentFragmentId != homeFragmentId) {
            if (currentFragmentId == productoDescriptionFragment) {
                navController.navigate(menu_fragment);
            } else {
                navController.navigate(homeFragmentId);
            }
        } else {
            super.onBackPressed();
        }
    }



}
