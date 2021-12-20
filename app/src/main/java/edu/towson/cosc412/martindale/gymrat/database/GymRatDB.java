package edu.towson.cosc412.martindale.gymrat.database;

import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

import edu.towson.cosc412.martindale.gymrat.database.entities.Equipment;
import edu.towson.cosc412.martindale.gymrat.database.entities.Exercise;
import edu.towson.cosc412.martindale.gymrat.database.entities.Session;
import edu.towson.cosc412.martindale.gymrat.database.entities.User;
import edu.towson.cosc412.martindale.gymrat.database.entities.WeightOverTime;
import edu.towson.cosc412.martindale.gymrat.database.entities.Workout;

public class GymRatDB {
    private static GymRatDB instance;
    private Connection connection;
    private Statement statement;
    private String currentUser = "";

    private GymRatDB() {
        Thread thread = new Thread(() -> {
            try {
                Log.i("DB Connection", "Attempting connection...");
                connection = DriverManager.getConnection("jdbc:mysql://gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com:3306/gymratdb", "admin", "VobGjT47CiM2A");
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                Log.i("DB Connection", "Successfully connected!");
            } catch (Exception e) {
                Log.i("DB Connection", "Failed to connect!");
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static GymRatDB getInstance() {
        if (instance == null) {
            instance = new GymRatDB();
        }
        return instance;
    }

    public boolean hasUser(String username){
        try{
            ResultSet result = executeQuery(String.format("Select username FROM User WHERE username = '%s'", username));
            if (result.next()){ // If the user exists
                String user = result.getString(1);
                if (username.equals(user)){
                    return true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public boolean login(String username, String password){
        try{
            ResultSet result = executeQuery(String.format("Select username, password FROM User WHERE username = '%s'", username));
            if (result.next()){ // If the user exists
                String user = result.getString(1);
                String pass = result.getString(2);
                if (username.equals(user) && password.equals(pass)){
                    currentUser = username;
                    return true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addNewUser(User user) {
        try {
            ResultSet userResult = executeQuery(String.format("Select * FROM User WHERE username = '%s'", user.username));
            if (!userResult.next()) {
                String insert = "INSERT INTO User(username, password, firstName, lastName, birthdate,height)\n";
                insert += String.format(Locale.ENGLISH, "VALUES('%s', '%s','%s','%s', '%s','%f')", user.username, user.password, user.firstName, user.lastName, user.getBirthdayDate(), user.height);
                executeUpdate(insert);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addNewRoutine(String routineName, ArrayList<Workout> workouts) {
        try {
            String insert = "INSERT INTO Routine(routineName, userName)\n";
            insert += String.format("VALUES('%s','%s')", routineName, currentUser);
            executeUpdate(insert);

            for (int i = 0; i < workouts.size(); i++) {
                Workout wd = workouts.get(i);
                String exerciseName = wd.exercise.name;
                int sets = wd.sets;
                int reps = wd.reps;

                insert = "INSERT INTO RoutineHasWorkout(exerciseName, sets, reps, position)";
                insert += String.format(Locale.ENGLISH, "VALUES('%s', '%s', '%s', '%d'", exerciseName, sets, reps, i);
                executeUpdate(insert);
            }

            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addNewExercise(Exercise exerciseData, Equipment equipmentData) {
        try {
            ResultSet exerciseResult = executeQuery(String.format("Select exerciseName FROM Exercise WHERE exerciseName = '%s'", exerciseData.name));

            if (!exerciseResult.next()) {
                String insert = "INSERT INTO Exercise(exerciseName,equipmentID,targetBodyPart, caloriesPerMinute, estimateTime)\n";
                insert += String.format(Locale.ENGLISH, "VALUES('%d', '%s', %s, %f, %f)", equipmentData.id, exerciseData.name, exerciseData.targetBodyPart, exerciseData.caloriesPerMinute, exerciseData.estimateTime);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addNewSession(User user, Session sessionData) {

        try {
            String insert = "INSERT INTO Sessions(userName, routineID, startDate, endDate)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', %s, %s)", user.username, sessionData.routine.id, sessionData.startDateTime, sessionData.endDateTime);
            executeUpdate(insert);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addNewEquipment(Equipment equipmentData) {
        try {
            String insert = "INSERT INTO Equipment(equipmentName)\n";
            insert += String.format("VALUES('%s')", equipmentData.name);
            executeUpdate(insert);
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addWorkout(Workout workoutData, Exercise excerciseData) {

        try {
            String insert = "INSERT INTO Workout(exerciseName,reps,sets,breakTime)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', '%d', '%f')", excerciseData.name, workoutData.reps, workoutData.sets, workoutData.breakTime);
            executeUpdate(insert);
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addWeightOverTime(WeightOverTime weightOverTime, User user) {
        try {
            ResultSet sessionResult = executeQuery(String.format(Locale.ENGLISH,"Select date FROM WeightOverTime WHERE date = '%s'", weightOverTime.date));
            if (!sessionResult.next()) {
                String insert = "INSERT INTO WeightOvertTime(date,username,weight)\n";
                insert += String.format(Locale.ENGLISH,"VALUES('%s', '%s', '%f')", weightOverTime.date, user.username, weightOverTime.weight);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void executeUpdate(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (connection != null) {
            return statement.executeQuery(sql);
        }
        return null;
    }
}