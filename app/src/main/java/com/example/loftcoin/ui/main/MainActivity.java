package com.example.loftcoin.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.LoftApp;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class MainActivity extends AppCompatActivity {

    @Inject FragmentFactory fragmentFactory;

    private MainComponent mainComponent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final BaseComponent baseComponent =
                ((LoftApp) newBase.getApplicationContext()).getComponent();
        mainComponent = DaggerMainComponent.builder().baseComponent(baseComponent).build();
        mainComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().setFragmentFactory(fragmentFactory);

        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        final NavController navController = Navigation.findNavController(this, R.id.main_host);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
        NavigationUI.setupWithNavController(
                binding.toolbar, navController, new AppBarConfiguration
                        .Builder(binding.bottomNav.getMenu())
                        .build()
        );

    }
}
