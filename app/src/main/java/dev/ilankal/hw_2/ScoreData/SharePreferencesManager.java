package dev.ilankal.hw_2.ScoreData;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesManager {
    private static volatile SharePreferencesManager instance = null;
    private SharedPreferences sharedPreferences;
    private static final String DATA_FILE = "DATA_FILE";


    private SharePreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(DATA_FILE, Context.MODE_PRIVATE);
    }

    public static SharePreferencesManager init(Context context) {
        if (instance == null) {
            synchronized (SharePreferencesManager.class){
                if (instance == null){
                    instance = new SharePreferencesManager(context);
                }
            }
        }
        return getInstance();
    }
    public static SharePreferencesManager getInstance() {
        return instance;
    }

    public String pullString(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}