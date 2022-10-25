package com.example.tdd;

import androidx.multidex.MultiDexApplication;

import io.easyprefs.Prefs;
import timber.log.Timber;

public class MainApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.initializeApp(this);
        Timber.plant(new Timber.DebugTree());
    }
}
