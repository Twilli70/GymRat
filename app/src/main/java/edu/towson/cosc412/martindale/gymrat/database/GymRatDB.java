package edu.towson.cosc412.martindale.gymrat.database;

import android.util.Log;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import edu.towson.cosc412.martindale.gymrat.database.entities.Equipment;
import edu.towson.cosc412.martindale.gymrat.database.entities.Exercise;
import edu.towson.cosc412.martindale.gymrat.database.entities.Routine;
import edu.towson.cosc412.martindale.gymrat.database.entities.Session;
import edu.towson.cosc412.martindale.gymrat.database.entities.User;
import edu.towson.cosc412.martindale.gymrat.database.entities.WeightOverTime;
import edu.towson.cosc412.martindale.gymrat.database.entities.Workout;

public class GymRatDB {
    private static GymRatDB instance;
    private Connection connection;
    private Statement statement;
    private String currentUser = "benny";

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

    public boolean hasUser(String username) {
        try {
            ResultSet result = statement.executeQuery(String.format("Select username FROM User WHERE username = '%s'", username));
            if (result.next()) {
                String user = result.getString(1);
                if (username.equals(user)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String username, String password) {
        try {
            ResultSet result = statement.executeQuery(String.format("Select username, password FROM User WHERE username = '%s'", username));
            if (result.next()) { // If the user exists
                String user = result.getString("username");
                String pass = result.getString("password");
                if (username.equals(user) && password.equals(pass)) {
                    currentUser = username;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean addNewUser(User user) {
        try {
            ResultSet userResult = statement.executeQuery(String.format("Select * FROM User WHERE username = '%s'", user.username));
            if (!userResult.next()) {
                String insert = "INSERT INTO User(username, password, firstName, lastName, birthdate,height)\n";
                insert += String.format(Locale.ENGLISH, "VALUES('%s', '%s','%s','%s', '%s','%f')", user.username, user.password, user.firstName, user.lastName, user.getBirthdayDate(), user.height);
                statement.executeUpdate(insert);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String newPassword){
        try {
            String insert = "UPDATE User\n";
            insert += String.format(Locale.ENGLISH, "SET password = %s\n", newPassword);
            insert += String.format(Locale.ENGLISH, "WHERE username = %s", username);
            statement.executeUpdate(insert);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Untested - Have fun
    public ArrayList<Session> getAllSessions(String username) {
        try {
            ResultSet result = statement.executeQuery(String.format("Select * FROM Sessions WHERE username = '%s' ORDER BY startDate", username));
            ArrayList<Session> sessions = new ArrayList<>();
            while (result.next()) {
                Session session = new Session();
                session.id = result.getInt("sessionID");
                session.username = result.getString("userName");
                session.startDateTime = LocalDateTime.parse(result.getString("startDate"));
                session.endDateTime = LocalDateTime.parse(result.getString("endDate"));
                session.caloriesBurned = result.getFloat("caloriesBurned");

                int routineID = result.getInt("routineID");
                ResultSet routineQuery = statement.executeQuery(String.format(Locale.ENGLISH,"SELECT * FROM Routine WHERE routineID = %d", routineID));
                if (routineQuery.next()){
                    Routine routine = new Routine();
                    routine.id = routineQuery.getInt("routineID");
                    routine.username = routineQuery.getString("username");
                    routine.name = routineQuery.getString("routineName");
                    session.routine = routine;

                    ArrayList<Workout> workouts = new ArrayList<>();
                    ResultSet routineHasWorkoutQuery = statement.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM RoutineHasWorkout WHERE routineID = %d ORDER BY position", routine.id));
                    while (routineHasWorkoutQuery.next()){
                        Workout workout = new Workout();
                        workout.reps = routineHasWorkoutQuery.getInt("reps");
                        workout.sets = routineHasWorkoutQuery.getInt("sets");
                        workout.breakTime = routineHasWorkoutQuery.getFloat("breakTime");

                        String exerciseName = routineHasWorkoutQuery.getString("exerciseName");
                        ResultSet exerciseQuery = statement.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM Exercise WHERE exerciseName = %s", exerciseName));
                        if(exerciseQuery.next()){
                            Exercise exercise = new Exercise();
                            exercise.name = exerciseQuery.getString("exerciseName");
                            exercise.equipmentID = exerciseQuery.getInt("equipmentID");
                            exercise.targetBodyPart = exerciseQuery.getString("targetBodyPart");
                            exercise.caloriesPerMinute = exerciseQuery.getFloat("caloriesPerMin");
                            exercise.estimateTime = exerciseQuery.getFloat("estimateTime");
                            workout.exercise = exercise;
                        }
                        workouts.add(workout);
                    }
                    routine.workouts = workouts;
                }
                else {
                    Log.e("ERROR", "This literally should not be possible, send help");
                }

                sessions.add(session);
            }
            return sessions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Untested
    public boolean addNewRoutine(String routineName, ArrayList<Workout> workouts) {
        try {
            String insert = "INSERT INTO Routine(username, routineName)\n";
            insert += String.format("VALUES('%s','%s')", currentUser, routineName);
            statement.executeUpdate(insert);

            for (int i = 0; i < workouts.size(); i++) {
                Workout wd = workouts.get(i);
                String exerciseName = wd.exercise.name;



                insert = "INSERT INTO RoutineHasWorkout(workoutID, routineID, exerciseName, position)";
                insert += String.format(Locale.ENGLISH, "VALUES('%d','%s', '%d')", wd.id , exerciseName, i);
                statement.executeUpdate(insert);
            }


            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Untested
    // TODO: calculate calories burned from given data
    public boolean saveSession(String username, int routineID, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            String insert = "INSERT INTO Sessions(userName, routineID, startDate, endDate)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', %s, %s)", username, routineID, startDateTime, endDateTime);
            statement.executeUpdate(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Untested
    public boolean addWorkout(Workout workoutData, Exercise exercise) {

        try {
            String insert = "INSERT INTO Workout(exerciseName,reps,sets,breakTime)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', '%d', '%f')", exercise.name, workoutData.reps, workoutData.sets, workoutData.breakTime);
            statement.executeUpdate(insert);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Untested
    public boolean addWeightOverTime(String username, LocalDate date, float weight) {
        try {
            ResultSet sessionResult = statement.executeQuery(String.format(Locale.ENGLISH, "Select date FROM WeightOverTime WHERE date = '%s'", date));
            if (!sessionResult.next()) {
                String insert = "INSERT INTO WeightOvertTime(date,username,weight)\n";
                insert += String.format(Locale.ENGLISH, "VALUES('%s', '%s', '%f')", date, username, weight);
                statement.executeUpdate(insert);

            } else {
                String update = String.format(Locale.ENGLISH, "UPDATE WeightOverTime SET weight = %f WHERE date = '%s'", weight, date);
                statement.executeUpdate(update);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}