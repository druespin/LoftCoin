package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

public class TestCoinsRepo implements CoinsRepo {

    public PublishSubject<List<Coin>> listings = PublishSubject.create();

    public Query lastListingQuery;

    @NonNull
    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {
        lastListingQuery = query;
        return listings;
    }

    @NonNull
    @Override
    public Single<Coin> coin(Currency currency, long id) {
        return Single.error(() -> new AssertionError("stub"));
    }

    @NonNull
    @Override
    public Single<Coin> nextCoin(@NonNull Currency currency, List<Integer> ids) {
        return Single.error(() -> new AssertionError("stub"));
    }

    @NonNull
    @Override
    public Observable<List<Coin>> topCoins(@NonNull Currency currency) {
        return Observable.error(() -> new AssertionError("stub"));
    }
}
