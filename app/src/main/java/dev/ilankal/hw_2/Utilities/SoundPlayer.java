package dev.ilankal.hw_2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SoundPlayer {
    private Context context;
    private Executor executor;
    MediaPlayer mediaPlayer;
    public SoundPlayer(Context context) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }
    public void playSound(int resID, boolean loop) {
        if (mediaPlayer == null){
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, resID);
                mediaPlayer.setLooping(loop);
                mediaPlayer.setVolume(1.0f,1.0f);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

            });
        }
    }
    public void stopSound() {
        if (mediaPlayer != null){
            executor.execute(() -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }
}

