package com.example.loftcoin.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loftcoin.R;
import com.example.loftcoin.ui.MainActivity;
import com.example.loftcoin.ui.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private final Handler handler = new Handler();

    private Runnable goNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(WelcomeActivity.WELCOME_KEY, true)) {
            goNext = () -> startActivity(new Intent(this, WelcomeActivity.class));
            }
        else {
            goNext = () -> startActivity(new Intent(this, MainActivity.class));
        }
        handler.postDelayed(goNext, 1500);
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(goNext);
        super.onStop();
    }
}
