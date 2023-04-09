package com.example.fullapp3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.side_nav);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getTitle().toString()){
                    case "QRCode Scanner":
                        scanQR();
                        break;
                    case "Save XML":
                        startActivity(new Intent(MainActivity.this, XMLSaveActivity.class));
                        break;
                    case "Logout":
                        logoutDialog();
                        break;
                }
                drawerLayout.close();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PagerFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getTitle().toString()){
                    case "Pager":
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PagerFragment()).commit();
                        break;
                    case "Recycler":
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new RecyclerFragment()).commit();
                        break;
                    case "QRCode":
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new QRCodeFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void scanQR(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        scan_launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> scan_launcher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            new AlertDialog.Builder(this)
                    .setTitle("Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getTitle().toString()){
            case "Logout":
                logoutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logoutDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want to logout?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
}