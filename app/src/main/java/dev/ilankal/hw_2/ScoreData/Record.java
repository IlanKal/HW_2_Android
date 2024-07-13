package dev.ilankal.hw_2.ScoreData;


import androidx.annotation.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Record {
    private String date;
    private String time;
    private int score;
    private double lat; // latitude
    private double lon; // latitude


    public Record(int score, double lat, double lon) {
        setDate(date);
        setTime(time);
        setScore(score);
        setLat(lat);
        setLon(lon);
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @NonNull
    @Override
    public String toString() {
        return "Record{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", score=" + score +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}


