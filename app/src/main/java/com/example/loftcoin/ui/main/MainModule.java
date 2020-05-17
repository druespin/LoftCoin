package com.example.loftcoin.ui.main;

import androidx.fragment.app.FragmentFactory;

import com.example.loftcoin.util.LoftFragmentFactory;

import dagger.Binds;
import dagger.Module;

@Module
class MainModue {

    @Binds
    abstract FragmentFactory fragmentFactory(LoftFragmentFactory impl);
}
