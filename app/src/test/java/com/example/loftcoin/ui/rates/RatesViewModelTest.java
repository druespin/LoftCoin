package com.example.loftcoin.ui.rates;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.FakeCoin;
import com.example.loftcoin.data.TestCoinsRepo;
import com.example.loftcoin.data.TestCurrencyRepo;
import com.example.loftcoin.util.TestRxSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.google.common.truth.Truth.assertThat;


@RunWith(AndroidJUnit4.class)
public class RatesViewModelTest {

    private RatesViewModel ratesVM;
    private TestCoinsRepo testCoinsRepo;
    private TestCurrencyRepo testCurrencyRepo;

    @Before
    public void setup() {
        testCoinsRepo = new TestCoinsRepo();
        testCurrencyRepo = new TestCurrencyRepo(ApplicationProvider.getApplicationContext());
        ratesVM = new RatesViewModel(testCoinsRepo, testCurrencyRepo, new TestRxSchedulers());
    }

    @Test
    public void coins_refreshing_and_uploading() {
        TestObserver<List<Coin>> coinsTest = ratesVM.coins().test();
        ratesVM.isRefreshing().test().assertValue(true);
        List<Coin> fakeCoins = Arrays.asList(new FakeCoin(), new FakeCoin());

        testCoinsRepo.listings.onNext(fakeCoins);
        ratesVM.isRefreshing().test().assertValue(false);
        coinsTest.assertValue(fakeCoins);

        CoinsRepo.Query query = testCoinsRepo.lastListingQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isTrue();

        ratesVM.switchSortingOrder();
        ratesVM.isRefreshing().test().assertValue(false);
        coinsTest.assertValue(fakeCoins);
        ratesVM.isRefreshing().test().assertValue(false);

        query = testCoinsRepo.lastListingQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isFalse();

    }
}