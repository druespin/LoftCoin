package com.example.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.CmcCoin;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;


public class RatesViewModel extends ViewModel {

    private final LiveData<List<Coin>> coins;
    private final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();

    @Inject
    public RatesViewModel(CoinsRepo coinsRepo) {
        final LiveData<CoinsRepo.Query> query = Transformations
                .map(forceRefresh, r -> {
                    isRefreshing.postValue(true);
                    return CoinsRepo.Query
                            .builder()
                            .forceUpdate(r)
                            .currency("USD")
                            .build();
                });
         final LiveData<List<Coin>> coins = Transformations.switchMap(query, coinsRepo::listings);
         this.coins = Transformations.map(coins, c -> {
             isRefreshing.postValue(false);
             return c;
         });
    }

    @NonNull
    LiveData<List<Coin>> coins() {
        return coins;
    }

    @NonNull
    LiveData<Boolean> isRefreshing() {
        return isRefreshing;
    }

    final void refresh() { forceRefresh.postValue(true); }
}
