package edu.towson.cosc412.martindale.gymrat.database.entities;

import java.sql.Date;

public class Session {

    public int id;
    public Routine routine;
    public Date startDateTime;
    public Date endDateTime;
    public User username;
    public float caloriesBurned;

}
