package com.example.loftcoin.util;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestRxSchedulers implements RxSchedulers {

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler cmp() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler main() {
        return Schedulers.trampoline();
    }
}
