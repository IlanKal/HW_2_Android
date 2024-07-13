package dev.ilankal.hw_2.ScoreData;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordList {
    private final String name = "Record";
    private ArrayList<Record> records = new ArrayList<>();

    public RecordList() {
    }

    public String getName() {
        return name;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getLast10Records() {
        // Sort the records based on date and time
        records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                int dateCompare = r1.getDate().compareTo(r2.getDate());
                if (dateCompare != 0) {
                    return dateCompare;
                } else {
                    return r1.getTime().compareTo(r2.getTime());
                }
            }
        });

        // Get the last 10 records (or fewer if there aren't 10 records)
        int size = records.size();
        int startIndex = size > 10 ? size - 10 : 0;

        return new ArrayList<>(records.subList(startIndex, size));
    }

    @NonNull
    @Override
    public String toString() {
        return "RecordList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
