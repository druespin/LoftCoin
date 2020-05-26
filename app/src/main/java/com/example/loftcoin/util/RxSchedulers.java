package com.example.loftcoin.util;


import io.reactivex.Scheduler;

public interface RxSchedulers {

    Scheduler io();
    Scheduler cmp();
    Scheduler main();
}
