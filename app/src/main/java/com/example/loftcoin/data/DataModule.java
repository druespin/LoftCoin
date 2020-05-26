package com.example.loftcoin.data;

import android.content.Context;

import androidx.room.Room;

import com.example.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class DataModule {

    @Provides
    static Moshi moshi(ExecutorService executor) {
        final Moshi moshi = new Moshi.Builder().build();
        return moshi.newBuilder()
            .add(CmcCoin.class, moshi.adapter(AutoValue_CmcCoin.class))
            .add(Listings.class, moshi.adapter(AutoValue_Listings.class))
            .build();
    }

    @Provides
    static Retrofit retrofit(OkHttpClient httpClient, Moshi moshi) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient.newBuilder()
                .addInterceptor(chain -> {
                    final Request request = chain.request();
                    return chain.proceed(request.newBuilder()
                            .addHeader(CmcApi.API_KEY, BuildConfig.API_KEY)
                            .build());
                })
                .build());
        builder.baseUrl(BuildConfig.API_ENDPOINT);
        builder.addConverterFactory(MoshiConverterFactory.create(moshi));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync());
        return builder.build();
    }

    @Provides
    static CmcApi cmcApi(Retrofit retrofit) {
        return retrofit.create(CmcApi.class);
    }

    @Provides
    @Singleton
    static LoftDatabase loftDatabase(Context context) {
        if (BuildConfig.DEBUG) {
            return Room.inMemoryDatabaseBuilder(context, LoftDatabase.class).build();
        } else {
            return Room.databaseBuilder(context, LoftDatabase.class, "loft.db").build();
        }
    }

    @Binds
    abstract CoinsRepo coinsRepo(CmcCoinsRepo impl);

    @Binds
    abstract CurrencyRepo currencyRepo(CurrencyRepoImpl impl);

    @Binds
    abstract WalletsRepo walletsRepo(WalletsRepoImpl impl);
}
