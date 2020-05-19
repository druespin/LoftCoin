package com.example.loftcoin.ui.rates;

import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.util.VMModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                RatesModule.class,
                VMModule.class
        }, dependencies = {
                BaseComponent.class
        }
)
abstract class RatesComponent {

    abstract ViewModelProvider.Factory vmFactory();
}
