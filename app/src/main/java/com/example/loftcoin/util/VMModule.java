package com.example.loftcoin.util;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class VMModule {

    @Binds
    abstract ViewModelProvider.Factory vmFactory(VMFactory impl);
}
