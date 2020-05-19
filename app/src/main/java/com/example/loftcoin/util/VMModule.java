package com.example.loftcoin.util;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
abstract class UtilsModule {

    @Binds
    abstract ViewModelProvider.Factory vmFactory(VMFactory impl);
}
