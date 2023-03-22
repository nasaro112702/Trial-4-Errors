package com.example.fullapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.main_side_nav);
        bottomNavigationView = findViewById(R.id.main_bottom_nav);

        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.close();
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getTitle().toString()){
                    case "Home":
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
                        break;
                    case "Shop":
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ShopFragment()).commit();
                        break;
                    case "Settings":
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new SettingsFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option_items, menu);

        return super.onCreateOptionsMenu(menu);
    }
}