package com.example.loftcoin.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.loftcoin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

@Singleton
public
class CurrencyRepoImpl implements CurrencyRepo {

    public static final String KEY_CURRENCY = "currency";
    private final Context context;
    private static SharedPreferences prefs;
    private static Map<String, Currency> availableCurrencies = new HashMap<>();

    @Inject
    public CurrencyRepoImpl(@NonNull Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        availableCurrencies.put("USD", Currency.create("$", "USD", context.getString(R.string.usd)));
        availableCurrencies.put("EUR", Currency.create("E", "EUR", context.getString(R.string.eur)));
        availableCurrencies.put("RUB", Currency.create("R", "RUB", context.getString(R.string.rub)));
    }

    @NonNull
    @Override
    public LiveData<List<Currency>> availableCurrencies() {
        final MutableLiveData<List<Currency>> liveData = new MutableLiveData<>();
        liveData.setValue(new ArrayList<>(availableCurrencies.values()));
        return liveData;
    }

    @NonNull
    @Override
    public Observable<Currency> currency() {
        return Observable.create(emitter -> {
            SharedPreferences.OnSharedPreferenceChangeListener listener = (prefs, key) -> {
                if (!emitter.isDisposed()) {
                    emitter.onNext(availableCurrencies.get(prefs.getString(key, "USD")));
                }
            };
            prefs.registerOnSharedPreferenceChangeListener(listener);
            emitter.setCancellable(() -> prefs.unregisterOnSharedPreferenceChangeListener(listener));
            emitter.onNext(availableCurrencies.get(prefs.getString(KEY_CURRENCY, "USD")));
        });
    }

    @Override
    public void updateCurrency(@NonNull Currency currency) {
        prefs.edit().putString(KEY_CURRENCY, currency.code()).apply();
    }

}
