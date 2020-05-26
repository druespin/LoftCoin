package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.Notifier;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.RxSchedulers;

public interface BaseComponent {

    Context context();
    WalletsRepo walletsRepo();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    ImageLoader imageLoader();
    RxSchedulers rxSchedulers();
    Notifier notifier();
}
