package com.example.loftcoin.widget;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    static Context context(Application app) {
        return app.getApplicationContext();
    }
}
