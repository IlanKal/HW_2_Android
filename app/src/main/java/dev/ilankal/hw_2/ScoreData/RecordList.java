package dev.ilankal.hw_2.ScoreData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordList {

    private final String name = "Records";
    private ArrayList<Record> records = new ArrayList<>();

    public RecordList() {

    }
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    //add a record to the list
    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getTop10Records() {
        // Sort records by score in descending order directly within this method
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return Integer.compare(r2.getScore(), r1.getScore());
            }
        });

        // Get the top 10 records or fewer if less than 10 records exist
        int limit = Math.min(records.size(), 10);
        return new ArrayList<>(records.subList(0, limit));
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
