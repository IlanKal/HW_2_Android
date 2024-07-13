package dev.ilankal.hw_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import dev.ilankal.hw_2.Fragments.ListFragment;
import dev.ilankal.hw_2.Fragments.MapFragment;
import dev.ilankal.hw_2.interfaces.Callback_ListItemClicked;

public class LeaderboardActivity extends AppCompatActivity implements Callback_ListItemClicked {

    private FrameLayout leaderboard_FRAME_list;
    private FrameLayout leaderboard_FRAME_map;
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private MaterialButton menu_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        findViews();
        initViews();
    }

    private void findViews() {
        leaderboard_FRAME_list = findViewById(R.id.leaderboard_FRAME_list);
        leaderboard_FRAME_map = findViewById(R.id.leaderboard_FRAME_map);
        menu_BTN = findViewById(R.id.menu_BTN);
    }

    private void initViews() {
        menu_BTN.setOnClickListener(v -> menuClicked());
        listFragment = new ListFragment();
        listFragment.setCallbackListItemClicked(this);

        // Show fragments
        getSupportFragmentManager().beginTransaction().add(R.id.leaderboard_FRAME_list, listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.leaderboard_FRAME_map, mapFragment).commit();
    }



    public void listItemClicked(double lat, double lon) {
        if (mapFragment != null) {
            mapFragment.zoom(lat, lon);
            Log.d("cords",  lat + " " + lon);
        }

    }


    private void menuClicked() {
        // Move to start activity
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
