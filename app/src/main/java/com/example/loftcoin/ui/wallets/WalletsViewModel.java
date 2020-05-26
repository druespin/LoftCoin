package com.example.loftcoin.ui.wallets;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

class WalletsViewModel extends ViewModel {

    private final Observable<List<Wallet>> wallets;
    private final RxSchedulers schedulers;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        wallets = currencyRepo.currency().switchMap(walletsRepo::wallets);
        this.schedulers = schedulers;
    }

    @NonNull
    public Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }
}
