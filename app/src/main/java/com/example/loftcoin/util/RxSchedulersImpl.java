package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersImpl implements RxSchedulers {

    private final Scheduler ioScheduler;

    @Inject
    RxSchedulersImpl(ExecutorService executor) {
        this.ioScheduler = Schedulers.from(executor);
    }

    @NonNull
    @Override
    public Scheduler io() {
        return ioScheduler;
    }

    @NonNull
    @Override
    public Scheduler cmp() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
