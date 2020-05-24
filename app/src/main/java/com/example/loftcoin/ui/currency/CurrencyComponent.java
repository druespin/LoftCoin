package com.example.loftcoin.ui.currency;

import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.util.VMModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CurrencyModule.class,
        VMModule.class
        },
            dependencies = {
                BaseComponent.class
            })
abstract class CurrencyComponent {

    abstract ViewModelProvider.Factory vmFactory();
}
