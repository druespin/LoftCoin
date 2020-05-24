package com.example.loftcoin.util;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public interface Formatter<T> {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    String format(@NonNull String currency, @NonNull Double value);

    @NonNull
    String format(@NonNull T value);
}
