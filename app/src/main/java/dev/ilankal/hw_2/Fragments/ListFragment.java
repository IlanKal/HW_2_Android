package dev.ilankal.hw_2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import dev.ilankal.hw_2.Adapter.LeaderboardAdapter;
import dev.ilankal.hw_2.R;
import dev.ilankal.hw_2.ScoreData.RecordList;
import dev.ilankal.hw_2.ScoreData.SharePreferencesManager;
import dev.ilankal.hw_2.interfaces.Callback_ListItemClicked;

public class ListFragment extends Fragment {

    private RecyclerView leaderboardRecyclerView;
    private final String LEADERBOARD_SPM = "recordList";
    private RecordList recordList;
    private Callback_ListItemClicked callbackListItemClicked;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked) {
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initListData();
        initViews();
        return v;
    }

    private void initViews() {
        LeaderboardAdapter recordAdapter = new LeaderboardAdapter(recordList.getTop10Records(), (lat, lon) -> {
            if (callbackListItemClicked != null) {
                callbackListItemClicked.listItemClicked(lat, lon);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        leaderboardRecyclerView.setLayoutManager(linearLayoutManager);
        leaderboardRecyclerView.setAdapter(recordAdapter);
    }

    private void findViews(View v) {
        leaderboardRecyclerView = v.findViewById(R.id.Leaderboard_LST);
    }

    private void initListData() {
        Gson gson = new Gson();
        // Getting data
        String recordListAsJson = SharePreferencesManager
                .getInstance()
                .pullString(LEADERBOARD_SPM, "");
        recordList = gson.fromJson(recordListAsJson, RecordList.class);
        if (recordList == null) {
            recordList = new RecordList();
        }
    }
}
