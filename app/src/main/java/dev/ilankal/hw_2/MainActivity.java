package dev.ilankal.hw_2;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import dev.ilankal.hw_2.Utilities.SoundPlayer;
import dev.ilankal.hw_2.Utilities.GPSLocationHelper;
import dev.ilankal.hw_2.Utilities.MoveDetector;
import dev.ilankal.hw_2.Logic.GameController;
import dev.ilankal.hw_2.ScoreData.Record;
import dev.ilankal.hw_2.ScoreData.RecordList;
import dev.ilankal.hw_2.ScoreData.SharePreferencesManager;
import dev.ilankal.hw_2.interfaces.MoveCallback;

public class MainActivity extends AppCompatActivity {
    private final String KEY_RECORDS_SPM = "recordList";
    private GPSLocationHelper GPSLocationHelper;
    private MoveDetector moveDetector;
    public SoundPlayer soundPlayer;
    private final String TIMER = "timer";
    private final String OTHER = "other";
    private ExtendedFloatingActionButton main_FAB_arrow_left;
    private ExtendedFloatingActionButton main_FAB_arrow_right;
    private AppCompatImageView[] main_IMG_hearts;
    private AppCompatImageView[] main_IMG_rocket;
    private AppCompatImageView[][] main_IMG_matrix_flaming_meteor;
    private GameController gameController;
    private long startTime;
    private boolean timerOn = false;
    private Timer timer;
    private long DELAY = 1000L;
    private MaterialTextView main_LBl_scoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        gameController = new GameController(main_IMG_hearts.length);
        gameModeAndSpeed();
        GPSLocationHelper = new GPSLocationHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (mode != null && mode.equals("sensors")) {
            moveDetector.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (mode != null && mode.equals("sensors")) {
            moveDetector.stop();
        }
    }
    private void findViews() {
        // scoreboard
        main_LBl_scoreboard = findViewById(R.id.main_LBl_scoreboard);

        // arrows buttons
        main_FAB_arrow_left = findViewById(R.id.main_FAB_arrow_left);
        main_FAB_arrow_right = findViewById(R.id.main_FAB_arrow_right);

        // array of hearts
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        // array of rocket
        main_IMG_rocket = new AppCompatImageView[]{
                findViewById(R.id.main_rocket_0),
                findViewById(R.id.main_rocket_1),
                findViewById(R.id.main_rocket_2),
                findViewById(R.id.main_rocket_3),
                findViewById(R.id.main_rocket_4)
        };

        // 2D array of flaming meteor
        main_IMG_matrix_flaming_meteor = new AppCompatImageView[][]{
                {
                        findViewById(R.id.main_index_00),
                        findViewById(R.id.main_index_01),
                        findViewById(R.id.main_index_02),
                        findViewById(R.id.main_index_03),
                        findViewById(R.id.main_index_04)
                },
                {
                        findViewById(R.id.main_index_10),
                        findViewById(R.id.main_index_11),
                        findViewById(R.id.main_index_12),
                        findViewById(R.id.main_index_13),
                        findViewById(R.id.main_index_14)
                },
                {
                        findViewById(R.id.main_index_20),
                        findViewById(R.id.main_index_21),
                        findViewById(R.id.main_index_22),
                        findViewById(R.id.main_index_23),
                        findViewById(R.id.main_index_24)
                },
                {
                        findViewById(R.id.main_index_30),
                        findViewById(R.id.main_index_31),
                        findViewById(R.id.main_index_32),
                        findViewById(R.id.main_index_33),
                        findViewById(R.id.main_index_34)
                },
                {
                        findViewById(R.id.main_index_40),
                        findViewById(R.id.main_index_41),
                        findViewById(R.id.main_index_42),
                        findViewById(R.id.main_index_43),
                        findViewById(R.id.main_index_44)
                },
                {
                        findViewById(R.id.main_index_50),
                        findViewById(R.id.main_index_51),
                        findViewById(R.id.main_index_52),
                        findViewById(R.id.main_index_53),
                        findViewById(R.id.main_index_54)
                },
        };
    }


    private void initViews() {
        main_FAB_arrow_left.setOnClickListener(v -> moveByClick("left"));
        main_FAB_arrow_right.setOnClickListener(v -> moveByClick("right"));
    }
    private void gameModeAndSpeed(){
        // Retrieve the mode passed from StartActivity
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        boolean isFastMode = intent.getBooleanExtra("fast", false);


        if (mode != null && mode.equals("sensors")) {
            // Hide the arrow buttons if sensors mode is selected
            main_FAB_arrow_left.setVisibility(View.INVISIBLE);
            main_FAB_arrow_right.setVisibility(View.INVISIBLE);

            // sensors mode method
            initMoveDetector();
        }
        if (isFastMode) {
            setupFastMode();
        }
    }
    private void setupFastMode() {
        DELAY = 700L;
    }
    private void initMoveDetector() {
        moveDetector = new MoveDetector(this,
                new MoveCallback() {
                    @Override
                    public void moveXLeft() {
                        moveByClick("left");
                    }

                    @Override
                    public void moveXRight() {
                        moveByClick("right");
                    }
                }
        );
    }

    private void reLoadMatrixTime() {
        gameController.moveMatrixSpot();
        reLoadUI(TIMER);
    }

    private void moveByClick(String dir) {
        gameController.moveRocket(dir); // move the rocket according to the arrow
        reLoadUI(dir);
    }

    private void startTimer() {
        if (!timerOn) {
            Log.d("startTimer", "startTimer: Timer Started");
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread((() -> reLoadMatrixTime()));
                }
            }, DELAY, DELAY); // Start after DELAY, repeat every DELAY
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timerOn = false;
            Log.d("stopTimer", "stopTimer: Timer Stopped");
            timer.cancel();
        }
    }

    private void reLoadUI(String reason) {
        if (gameController.lostGame()) {
            stopTimer();
            Log.d("lostGame", "You lost the game!!!");
            updateLeaderboard();
            moveToStartActivity();
            return;
        } else {
            //update matrix
            updateMatrixUI();
            //move rocket
            displayRocketUI();
            //update hearts
            updateHeartsUI();
            //update scoreBoard
            if (!(reason.equals("left") && gameController.getRocketPos() == 0) &&
                    !(reason.equals("right") && gameController.getRocketPos() == gameController.getMatrixCols() -1)){
                updateScoreBoardUI(reason);
            }
        }
    }

    private void updateMatrixUI() {
        for (int i = 0; i < gameController.getMatrixRows(); i++) {
            for (int j = 0; j < gameController.getMatrixCols(); j++) {
                String currentCol = gameController.getMatrix()[i][j];
                if (currentCol.equals(gameController.getFLAMING_METEOR())) {
                    main_IMG_matrix_flaming_meteor[i][j].setImageResource(R.drawable.flaming_meteor);
                    main_IMG_matrix_flaming_meteor[i][j].setVisibility(View.VISIBLE);
                } else if (currentCol.equals(gameController.getBLANK())) {
                    main_IMG_matrix_flaming_meteor[i][j].setVisibility(View.INVISIBLE);
                }
                else if (currentCol.equals(gameController.getCOIN())){
                    main_IMG_matrix_flaming_meteor[i][j].setImageResource(R.drawable.coin);
                    main_IMG_matrix_flaming_meteor[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void displayRocketUI() {
        Log.d("Position", "" + gameController.getRocketPos());
        for (int i = 0; i < gameController.getMatrixCols(); i++) {
            if (i == gameController.getRocketPos()) {
                main_IMG_rocket[i].setVisibility(View.VISIBLE);
            } else {
                main_IMG_rocket[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void updateHeartsUI() {
        if (gameController.checkCollision()) {
            soundPlayer = new SoundPlayer(this);
            soundPlayer.playSound(R.raw.crash_sound, false);
            if(gameController.getLife() == 0){
                toastAndVibrate("You lost the game!");
            }
            toastAndVibrate("Collision number " + gameController.getAmountOfDisqualifications());
            main_IMG_hearts[gameController.getAmountOfDisqualifications() - 1].setVisibility(View.INVISIBLE);
        }
    }
    private void updateScoreBoardUI(String reason) {
        if(reason.equals(TIMER)){
            gameController.updateCoinsByTimer();
            main_LBl_scoreboard.setText(String.valueOf("score: " + gameController.getScoreBoard()));

        }
        if (gameController.checkTookCoin()) {
            main_LBl_scoreboard.setText(String.valueOf("score: " + gameController.getScoreBoard()));
            soundPlayer = new SoundPlayer(this);
            soundPlayer.playSound(R.raw.collect_coin, false);
        }
    }

    private void moveToStartActivity() {
        //move to start activity
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateLeaderboard(){
        Gson gson = new Gson();
        //getting data
        String recordListAsJson = SharePreferencesManager.getInstance().getString(KEY_RECORDS_SPM, "");
        RecordList recordList = gson.fromJson(recordListAsJson, RecordList.class);
        if (recordList == null){
            recordList = new RecordList();
        }
        Record newRecord = new Record(gameController.getScoreBoard(), GPSLocationHelper.getLat(), GPSLocationHelper.getLon());
        recordList.addRecord(newRecord);
        Log.d("RecordList", recordList.toString());
        String newRecordListAsJson = gson.toJson(recordList);
        //saving data
        SharePreferencesManager
                .getInstance()
                .putString(KEY_RECORDS_SPM, newRecordListAsJson);
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GPSLocationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
