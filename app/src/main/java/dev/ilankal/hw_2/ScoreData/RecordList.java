package dev.ilankal.hw_2.ScoreData;

import androidx.annotation.NonNull;

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

    public ArrayList<Record> getRecordsTop10() {
        // Sort records by default comparator (if needed)
        sortRecords(new RecordPointsComparator());

        // Get the top 10 records or fewer if less than 10 records exist
        int limit = Math.min(records.size(), 10);
        ArrayList<Record> recordsTop10 = new ArrayList<>(records.subList(0, limit));
        return recordsTop10;
    }

    // Method to add a record to the list
    public void addRecord(Record record) {
        records.add(record);
    }

    // Method to sort records based on a comparator
    public void sortRecords(Comparator<Record> comparator) {
        Collections.sort(records, comparator);
    }

    // comparator to sort records by a specific field (e.g., record points)
    public static class RecordPointsComparator implements Comparator<Record> {
        @Override
        public int compare(Record r1, Record r2) {
            return Integer.compare(r2.getScore(), r1.getScore());
        }
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}