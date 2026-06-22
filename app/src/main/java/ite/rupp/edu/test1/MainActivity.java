package ite.rupp.edu.test1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Find the custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 2. Find the NavHostFragment to get the NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // 3. Define top-level destinations (screens where the back arrow should NOT show)
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.homeFragment, R.id.cartFragment, R.id.ordersFragment)
                    .build();

            // 4. Setup Toolbar with NavController directly
            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

            // 5. Setup Bottom Navigation
            BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
            if (bottomNav != null) {
                NavigationUI.setupWithNavController(bottomNav, navController);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController != null
                && NavigationUI.navigateUp(navController, appBarConfiguration);
    }
}
