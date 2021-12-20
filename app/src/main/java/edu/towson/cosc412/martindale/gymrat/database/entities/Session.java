package edu.towson.cosc412.martindale.gymrat.database.entities;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Session {

    public int id;
    public Routine routine;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public String username;
    public float caloriesBurned;

    public float getDuration(){
        if (startDateTime != null && endDateTime != null){
            Duration duration = Duration.between(startDateTime, endDateTime);
            return duration.getSeconds();
        }
        return  -1;
    }
}
