package edu.towson.cosc412.martindale.gymrat.database;

public class Exercise {
    private enum BodyPart{
        Chest,
        Back,
        Arms,
        Abdominal,
        Legs,
        Shoulders,
    }

    public String name;
    public String equipment;
    public BodyPart targetBodyPart;
    public double caloriesPerMinute;
}
