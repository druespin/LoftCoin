package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;

public class PercentFormatter implements Formatter<Double> {

    @Inject
    PercentFormatter() {
    }

    @NonNull
    @Override
    public String format(@NonNull String currency, @NonNull Double value) {
        return null;
    }

    @NonNull
    @Override
    public String format(@NonNull Double value) {
        return String.format(Locale.US, "%.2f%%", value);
    }
}
