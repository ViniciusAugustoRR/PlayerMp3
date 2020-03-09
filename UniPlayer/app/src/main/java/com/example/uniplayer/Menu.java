package com.example.uniplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.uniplayer.fragments.AlbumsFragment;
import com.example.uniplayer.fragments.FaixaFragment;
import com.example.uniplayer.fragments.PlaylistFragment;

import java.io.File;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    FrameLayout containerFL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        containerFL = findViewById(R.id.frag_container);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        bottomNav.getMenu().getItem(1).setChecked(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, new FaixaFragment()).commit();

    }




    //BOTTOM NAV BAR
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;

                    switch (item.getItemId()){

                        case R.id.faixas_nav:
                            selectedFrag = new FaixaFragment();
                            break;

                        case R.id.albums_nav:
                            selectedFrag = new AlbumsFragment();
                            break;

                        case R.id.playlist_nav:
                            selectedFrag = new PlaylistFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, selectedFrag).commit();

                    return true;
                }
            };


}
