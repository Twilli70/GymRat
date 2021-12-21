package edu.towson.cosc412.martindale.gymrat.database.entities;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {
    public int id;
    public Routine routine;
    public String username;
    public float caloriesBurned;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;


    public void setStartDateTime(String date){
        startDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
    }

    public void setEndDateTime(String date){
        endDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
    }

    public void setStartDateTime(int year, int month, int day, int hours, int minutes, int seconds){
        startDateTime = LocalDateTime.of(year, month, day, hours, minutes, seconds);
    }

    public void setEndDateTime(int year, int month, int day, int hours, int minutes, int seconds){
        endDateTime = LocalDateTime.of(year, month, day, hours, minutes, seconds);
    }

    public String getStartDateTime(){
        return startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getEndDateTime(){
        return endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public float getDuration(){
        if (startDateTime != null && endDateTime != null){
            Duration duration = Duration.between(startDateTime, endDateTime);
            return duration.getSeconds();
        }
        return  -1;
    }
}
