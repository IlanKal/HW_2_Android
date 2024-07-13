package dev.ilankal.hw_2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import dev.ilankal.hw_2.Utilities.SoundPlayer;

public class StartActivity extends AppCompatActivity {
    public SoundPlayer soundPlayer;
    private MaterialTextView main_TXT_text;
    private MaterialButton main_FAB_buttons;
    private MaterialButton main_FAB_sensors;
    private MaterialButton main_FAB_leaderboard;
    private RadioButton radio_fast_mode;
    private boolean isFastMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playSound(R.raw.main_menu_sound, true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSound();
    }

    private void findViews() {
        main_TXT_text = findViewById(R.id.main_TXT_text);
        main_FAB_buttons = findViewById(R.id.main_FAB_buttons);
        main_FAB_sensors = findViewById(R.id.main_FAB_sensors);
        main_FAB_leaderboard = findViewById(R.id.main_FAB_leaderboard);
        radio_fast_mode = findViewById(R.id.radio_fast_mode);
    }

    private void initViews() {
        main_FAB_buttons.setOnClickListener(view -> startMainActivity("buttons"));
        main_FAB_sensors.setOnClickListener(view -> startMainActivity("sensors"));
        radio_fast_mode.setOnClickListener(view -> isFastMode = radio_fast_mode.isChecked());
        main_FAB_leaderboard.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });
    }


    private void startMainActivity(String mode) {
        soundPlayer.stopSound();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playSound(R.raw.click_button, false);
        soundPlayer.stopSound();
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("fast", isFastMode);
        startActivity(intent);
    }


}