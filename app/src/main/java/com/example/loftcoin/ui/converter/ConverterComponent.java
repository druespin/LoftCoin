package com.example.loftcoin.ui.converter;

import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.util.VMModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ConverterModule.class,
        VMModule.class
        }, dependencies = {
                BaseComponent.class
        })
abstract class ConverterComponent {

    abstract ViewModelProvider.Factory vmFactory();

    abstract CoinsDialogAdapter coinsDialogAdapter();
}
