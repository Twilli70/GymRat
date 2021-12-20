package edu.towson.cosc412.martindale.gymrat.database;

import java.sql.Date;

public class SessionData {

    public int sessionID;
    public RoutineData routine;
    public Date startDateTime;
    public Date endDateTime;
    public UserData username;
    public float caloriesBurned;

}
