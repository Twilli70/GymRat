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

    public boolean updatePassword(String username, String newPassword) {
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
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(String.format("Select * FROM Sessions WHERE username = '%s' ORDER BY startDate", username));
            ArrayList<Session> sessions = new ArrayList<>();
            while (result.next()) {
                Session session = new Session();
                session.id = result.getInt("sessionID");
                session.username = result.getString("userName");
                session.setStartDateTime(result.getString("startDate"));
                session.setEndDateTime(result.getString("endDate"));
                session.caloriesBurned = result.getFloat("caloriesBurned");

                int routineID = result.getInt("routineID");
                st = connection.createStatement();
                ResultSet routineQuery = st.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM Routine WHERE routineID = %d", routineID));
                if (routineQuery.next()) {
                    Routine routine = new Routine();
                    routine.id = routineQuery.getInt("routineID");
                    routine.username = routineQuery.getString("username");
                    routine.name = routineQuery.getString("routineName");
                    session.routine = routine;

                    ArrayList<Workout> workouts = new ArrayList<>();
                    st = connection.createStatement();
                    ResultSet routineHasWorkoutQuery = st.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM RoutineHasWorkout WHERE routineID = %d ORDER BY position", routine.id));
                    while (routineHasWorkoutQuery.next()) {
                        int workoutID = routineHasWorkoutQuery.getInt("workoutID");
                        st = connection.createStatement();
                        ResultSet workoutQuery = st.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM Workout WHERE workoutID = %d", workoutID));

                        String exerciseName;
                        int reps;
                        int sets;
                        float breakTime;

                        if (workoutQuery.next()) {
                            exerciseName = workoutQuery.getString("exerciseName");
                            reps = workoutQuery.getInt("reps");
                            sets = workoutQuery.getInt("sets");
                            breakTime = workoutQuery.getFloat("breakTime");
                            Exercise exercise = null;
                            ResultSet exerciseQuery = st.executeQuery(String.format(Locale.ENGLISH, "SELECT * FROM Exercise WHERE exerciseName = '%s'", exerciseName));

                            if (exerciseQuery.next()) {
                                exercise = new Exercise();
                                exercise.name = exerciseQuery.getString("exerciseName");
                                exercise.equipmentID = exerciseQuery.getInt("equipmentID");
                                exercise.targetBodyPart = exerciseQuery.getString("targetBodyPart");
                                exercise.caloriesPerMinute = exerciseQuery.getFloat("caloriesPerMin");
                                exercise.estimateTime = exerciseQuery.getFloat("estimateTime");
                            }
                            Workout workout = new Workout(workoutID, exercise, reps, sets, breakTime);
                            workouts.add(workout);
                        }
                    }
                    routine.workouts = workouts;
                } else {
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

    public boolean addNewRoutine(String routineName, String username, ArrayList<Integer> workoutIDs) {
        try {
            String insert = "INSERT INTO Routine(username, routineName)\n";
            insert += String.format("VALUES('%s','%s')", username, routineName);
            statement.executeUpdate(insert);
            ResultSet routineSearch = statement.executeQuery("SELECT MAX(routineID) AS newID FROM Routine");
            routineSearch.next();

            int routineID = routineSearch.getInt("newID");

            for (int i = 0; i < workoutIDs.size(); i++) {
                int workoutID = workoutIDs.get(i);
                insert = "INSERT INTO RoutineHasWorkout(workoutID, routineID, position)";
                insert += String.format(Locale.ENGLISH, "VALUES('%d','%s', '%d')", workoutID, routineID, i);
                statement.executeUpdate(insert);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Works but date values and calories burned are hard coded.
    // TODO: calculate calories burned from given data
    public boolean saveSession(String username, int routineID, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            String insert = "INSERT INTO Sessions(userName, routineID, startDate, endDate, caloriesBurned)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', '%s', '%s', %f)", username, routineID, "2021-12-20 01:02:03", "2021-12-20 01:20:03", 100f);
            statement.executeUpdate(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addWorkout(String exerciseName, int reps, int sets, float breakTime) {

        try {
            String insert = "INSERT INTO Workout(exerciseName,reps,sets,breakTime)\n";
            insert += String.format(Locale.ENGLISH, "VALUES('%s', '%d', '%d', '%f')", exerciseName, reps, sets, breakTime);
            statement.executeUpdate(insert);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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