package dev.ilankal.hw_2.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.ilankal.hw_2.R;
import dev.ilankal.hw_2.ScoreData.Record;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>{
    private OnItemClickListener onItemClickListener;
    private final ArrayList<Record> records_list;

    public interface OnItemClickListener {
        void onItemClick(double lat, double lon);
    }

    public LeaderboardAdapter(ArrayList<Record> records_list, OnItemClickListener onItemClickListener) {
        this.records_list = records_list;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        int color;
        Record record = getItem(position);

        holder.item_date.setText(record.getDate());
        holder.item_time.setText(record.getTime());
        holder.item_score.setText(String.valueOf(record.getScore()));

        //set the background color
        if(position % 2 == 0){
            color = ContextCompat.getColor(holder.itemView.getContext(), R.color.purple);
            holder.item_container.setBackgroundColor(color);
        } else {
            color = ContextCompat.getColor(holder.itemView.getContext(), R.color.bright_purple);
            holder.item_container.setBackgroundColor(color);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(record.getLat(), record.getLon());
            }
        });
    }
    private Record getItem(int position) {
        return records_list.get(position);
    }
    @Override
    public int getItemCount() {
        return records_list.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutCompat item_container;
        private final TextView item_date;
        private final TextView item_time;
        private final TextView item_score;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_date = itemView.findViewById(R.id.item_date);
            item_time = itemView.findViewById(R.id.item_time);
            item_score = itemView.findViewById(R.id.item_score);
        }
    }
}