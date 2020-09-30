package com.example.runningman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WarehouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

//        BottomNavigationView typeNav = findViewById(R.id.type_navigation);
//        typeNav.setOnNavigationItemSelectedListener(navListener);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new BuildingFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrgament = null;

                    switch (item.getItemId()) {
                        case R.id.nav_building:
                            selectedFrgament = new BuildingFragment();
                            break;
                        case R.id.nav_tree:
                            selectedFrgament = new TreeFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFrgament).commit();
                    return true;
                }
            };

    public void ShowPopup(View view) {

//        Dialog popup = new Dialog(this);
        setContentView(R.layout.warehouse_popup);
        BottomNavigationView typeNav = findViewById(R.id.type_navigation);
        typeNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new BuildingFragment()).commit();
//        popup.show();



    }
}