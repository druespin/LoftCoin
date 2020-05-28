package com.example.loftcoin.ui.wallets;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.Transaction;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

class WalletsViewModel extends ViewModel {

    private final Observable<List<Wallet>> wallets;
    private final Observable<List<Transaction>> transactions;
    private final BehaviorSubject<Integer> walletPosition = BehaviorSubject.createDefault(0);
    private final RxSchedulers schedulers;
    private final WalletsRepo walletsRepo;
    private final CurrencyRepo currencyRepo;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;
        this.currencyRepo = currencyRepo;
        this.walletsRepo = walletsRepo;

        wallets = currencyRepo
                .currency()
                .switchMap(walletsRepo::wallets)
                .replay(1)
                .autoConnect();

        transactions = wallets
                .switchMap((wallets) -> walletPosition.map(wallets::get))
                .switchMap(walletsRepo::transactions)
                .replay(1)
                .autoConnect();
    }

    @NonNull
    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }

    @NonNull
    Observable<List<Transaction>> transactions() {
        return transactions.observeOn(schedulers.main());
    }

    @NonNull
    Completable addWallet() {
        return wallets
            .switchMapSingle((list) -> Observable
                    .fromIterable(list)
                    .map(Wallet::coin)
                    .map(Coin::id)
                    .toList()
            )
            .switchMapCompletable((ids) -> currencyRepo
                    .currency()
                    .firstOrError()
                    .map((c) -> walletsRepo.addWallet(c, ids))
                    .ignoreElement()
            )
            .observeOn(schedulers.main());
    }

    void changeWallet(int position) {
        walletPosition.onNext(position);
    }
}
