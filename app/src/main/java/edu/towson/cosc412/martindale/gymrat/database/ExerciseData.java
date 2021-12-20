package edu.towson.cosc412.martindale.gymrat.database;

import java.util.Date;

public class ExerciseData {
    private enum BodyPart {
        Chest,
        Back,
        Arms,
        Abdominal,
        Legs,
        Shoulders,
    }

    public String exerciseName;
    public String equipment;
    public BodyPart targetBodyPart;
    public double caloriesPerMinute;
    public Date estimateTime;

}