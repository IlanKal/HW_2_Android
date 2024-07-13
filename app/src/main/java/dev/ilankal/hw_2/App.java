package dev.ilankal.hw_2;

import android.app.Application;

import dev.ilankal.hw_2.ScoreData.SharePreferencesManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManager.init(this);
    }
}
