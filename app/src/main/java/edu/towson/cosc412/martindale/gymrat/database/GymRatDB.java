package edu.towson.cosc412.martindale.gymrat.database;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GymRatDB {
    public static final String host = "gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com";
    public static final int port = 3306;
    public static final String dbName = "gymratdb";
    public static final String dbUser = "admin";
    public static final String dbPassword = "VobGjT47CiM2A";

    public static GymRatDB instance;

    Connection connection;
    Statement statement;

    private GymRatDB() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Attempting to connect to database...");
                    connection = DriverManager.getConnection("jdbc:mysql://gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com:3306/gymratdb", "admin", "VobGjT47CiM2A");
                    statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    System.out.println("Successfully connected to database!");
                } catch (Exception e) {
                    System.out.println("Failed to connect to database");
                    e.printStackTrace();
                }
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

    public int addNewUser(UserData userData) {
        try {
            ResultSet userResult = executeQuery(String.format("Select * FROM User WHERE username = '%s'", userData.username));
            if (!userResult.next()) {
                String insert = "INSERT INTO User(username, password, firstName, lastName, birthdate,height)\n";

                insert += String.format(Locale.ENGLISH, "VALUES('%s', '%s','%s','%s', '%s','%f')", userData.username, userData.password, userData.firstName, userData.lastName, userData.birthdayDate, userData.height);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addNewRoutine(UserData userData, RoutineData routineData) {
        try {

            ResultSet routineResult = executeQuery(String.format("Select routineID FROM Routine WHERE routineID = '%d'", routineData.routineID));
            if (!routineResult.next()) {
                String insert = "INSERT INTO Routine(routineName, userName)\n";
                insert += String.format("VALUES('%s','%s')", routineData.routineName, userData.username);
                executeUpdate(insert);

                for (int i = 0; i < routineData.workouts.size(); i++) {
                    WorkoutData wd = routineData.workouts.get(i);
                    String exerciseName = wd.exerciseData.exerciseName;
                    int sets = wd.sets;
                    int reps = wd.reps;

                    insert = "INSERT INTO RoutineHasWorkout(exerciseName, sets, reps, position)";
                    insert += String.format("VALUES('%s', '%s', '%s', '%d'", exerciseName, sets, reps, i);
                    executeUpdate(insert);
                }

                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public int addNewExercise(ExerciseData exerciseData, EquipmentData equipmentData) {
        try {
            ResultSet exerciseResult = executeQuery(String.format("Select exerciseName FROM Exercise WHERE exerciseName = '%s'", exerciseData.exerciseName));

            if (!exerciseResult.next()) {
                String insert = "INSERT INTO Exercise(exerciseName,equipmentID,targetBodyPart, caloriesPerMinute, estimateTime)\n";
                insert += String.format(Locale.ENGLISH, "VALUES('%d', '%s', %s, %f)", equipmentData.equipmentID, exerciseData.exerciseName, exerciseData.targetBodyPart, exerciseData.caloriesPerMinute, exerciseData.estimateTime);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public int addNewSession(UserData userData, SessionData sessionData) {

        sessionData.sessionID = Integer.parseInt(selectMax("Sessions", "sessionID")) + 1;
        try {
            ResultSet sessionResult = executeQuery(String.format("Select sessionID FROM Sessions WHERE sessionID ='%d' ", sessionData.sessionID));
            if (!sessionResult.next()) {
                String insert = "INSERT INTO Sessions(userName, routineID, startDate, endDate)\n";
                insert += String.format("VALUES('%s', '%d', %s, %s)", userData.username, sessionData.routine.routineID, sessionData.startDateTime, sessionData.endDateTime);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public int addNewEquipment(EquipmentData equipmentData) {
        try {
            ResultSet equipmentResult = executeQuery(String.format("Select equipmentID FROM Equipment WHERE equipmentID = '%d'", equipmentData.equipmentID));
            if (!equipmentResult.next()) {
                String insert = "INSERT INTO Equipment(equipmentName)\n";
                insert += String.format("VALUES('%s')", equipmentData.equipmentName);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public int addWorkout(WorkoutData workoutData, ExerciseData excerciseData) {

        try {
            ResultSet workoutResult = executeQuery(String.format("Select exerciseName FROM Workout WHERE exerciseName = ", excerciseData.exerciseName));
            if (!workoutResult.next()) {
                String insert = "INSERT INTO Workout(exerciseName,reps,sets,breakTime)\n";
                insert += String.format("VALUES('%s', '%d', '%d', '%f')", excerciseData.exerciseName, workoutData.reps, workoutData.sets, workoutData.breakTime);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public int addWeightOverTime(WeightOverTime weightOverTime, UserData userData) {
        try {
            ResultSet sessionResult = executeQuery(String.format("Select date FROM WeightOverTime WHERE date = ", weightOverTime.date));
            if (!sessionResult.next()) {
                String insert = "INSERT INTO WeightOvertTime(date,username,weight)\n";
                insert += String.format("VALUES('%s', '%s', '%f')", weightOverTime.date, userData.username, weightOverTime.weight);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    public String selectMax(String table, String attribute) {
        try {
            ResultSet result = executeQuery(String.format("SELECT MAX(%s) FROM %s", attribute, table));
            result.next();
            return result.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void insertStringInto(String table, String[] columns, String[] values) {
        try {
            if (columns.length != values.length) {
                System.out.println("insertion amount does not match column amount");
                return;
            }

            StringBuilder insertion = new StringBuilder();
            insertion.append("INSERT INTO " + table + "(");
            for (int i = 0; i < columns.length; i++) {
                insertion.append(columns[i]);
                if (i < columns.length - 1) {
                    insertion.append(",");
                }
            }
            insertion.append(")\n");

            insertion.append("VALUES(");
            for (int i = 0; i < values.length; i++) {
                insertion.append("\"" + values[i] + "\"");
                if (i < values.length - 1) {
                    insertion.append(",");
                }
            }
            insertion.append(");");
            System.out.println(insertion);
            statement.executeUpdate(insertion.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String[] getResultColumns(ResultSet result) {
        try {
            ResultSetMetaData resultMeta = result.getMetaData();
            int columnCount = resultMeta.getColumnCount();
            String[] columnNames = new String[columnCount];
            ArrayList<String[]> rowEntries = new ArrayList<String[]>();

            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = resultMeta.getColumnName(i);
            }
            return columnNames;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[][] getResultRows(ResultSet result) {
        try {
            ResultSetMetaData resultMeta = result.getMetaData();
            int columnCount = resultMeta.getColumnCount();
            ArrayList<String[]> rowList = new ArrayList<String[]>();

            while (result.next()) {
                String[] rowEntry = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowEntry[i - 1] = result.getString(i);
                }
                rowList.add(rowEntry);
            }
            if (rowList.size() > 0) {
                String[][] rows = new String[rowList.size()][rowList.get(0).length];
                for (int i = 0; i < rowList.size(); i++) {
                    rows[i] = rowList.get(i);
                }
                return rows;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void printResult(ResultSet result) {
        if (result != null) {
            try {
                ResultSetMetaData resultMeta = result.getMetaData();
                int columns = resultMeta.getColumnCount();
                ArrayList<String> rows = new ArrayList<String>();
                StringBuilder format = new StringBuilder();

                result.beforeFirst();

                for (int i = 1; i <= columns; i++) {
                    rows.add("[ " + resultMeta.getColumnName(i) + " ]");
                    format.append("%").append(i).append("$-35s");
                }

                System.out.format(format + "\n", rows.toArray());

                rows.clear();
                while (result.next()) {
                    for (int i = 1; i <= columns; i++) {
                        String columnValue = result.getString(i);
                        rows.add("| " + columnValue + " |");
                    }
                    System.out.format(format + "\n", rows.toArray());
                }

                // May be unnecessary, intended show sql errors in console
                SQLWarning warning = result.getWarnings();
                while (warning != null) {
                    System.out.println(warning.getMessage());
                    warning = warning.getNextWarning();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}