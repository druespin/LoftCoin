package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;

public interface BaseComponent {

    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
}
