package com.example.loftcoin.util;

import com.example.loftcoin.data.Notifier;
import com.example.loftcoin.data.NotifierImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UtilModule {

    @Binds
    abstract ImageLoader imageLoader(PicassoImageLoader impl);

    @Binds
    abstract RxSchedulers rxSchedulers(RxSchedulersImpl impl);

    @Binds
    abstract Notifier notifier(NotifierImpl impl);
}
