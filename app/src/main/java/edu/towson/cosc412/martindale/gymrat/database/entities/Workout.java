package edu.towson.cosc412.martindale.gymrat.database.entities;

public class Workout {
    public int id;
    public Exercise exercise;
    public int sets;
    public int reps;
    public float breakTime;

    public Workout(int id, Exercise exercise, int sets, int reps, float breakTime) {
        this.id = id;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.breakTime = breakTime;
    }
}
