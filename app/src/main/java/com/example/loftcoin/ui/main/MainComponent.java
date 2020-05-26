package com.example.loftcoin.ui.main;


import com.example.loftcoin.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
                MainModule.class
        }, dependencies = {
                BaseComponent.class
        })
abstract class MainComponent {

    abstract void inject(MainActivity activity);
}
