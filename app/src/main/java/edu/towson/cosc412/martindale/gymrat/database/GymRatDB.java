package edu.towson.cosc412.martindale.gymrat.database;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GymRatDB {
    class Task extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                System.out.println("Connecting To Database...");
                DriverManager.getConnection("jdbc:mysql://gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com:3306/gymratdb", "admin", "VobGjT47CiM2A");
                System.out.println("Database Connection success");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static final String host = "gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com";
    private static final int port = 3306;
    private static final String dbName = "gymratdb";
    private static final String dbUser = "admin";
    private static final String dbPassword = "VobGjT47CiM2A";
    private final ApplicationExecutors executors = new ApplicationExecutors();;

    private static GymRatDB instance;

    Connection connection;
    Statement statement;

    private GymRatDB() {
        connectToDatabase();
    }

    private void connectToDatabase(){
        executors.getBackground().execute(() -> {
            try {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                String url = "jdbc:mysql://gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com:3306/gymratdb";

                System.out.println("Attempt to connect to database.");
                connection = DriverManager.getConnection(url, "admin", "VobGjT47CiM2A");
                System.out.println("Successfully connected to database.");

                System.out.println("Attempt to create statement");
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                System.out.println("Successfully created statement");

            } catch (Exception e) {
                System.out.println("Failed to connect to database.");
                e.printStackTrace();
            }
        });
    }

    public static GymRatDB getInstance() {
        if (instance == null) {
            instance = new GymRatDB();
        }
        return instance;
    }

    public void Test(){
        new Task().doInBackground();
    }

    public boolean hasUser(UserData userData){
        String sql = "SELECT * FROM User WHERE username = '" + userData.username + "'";
        try{
            ResultSet result = executeQuery(sql);
            System.out.println("Pizza");
            System.out.println(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void addNewUser(UserData userData) {
        String sql = "SELECT * FROM User WHERE username = " + userData.username;
        try{
            ResultSet result = executeQuery(sql);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int addNewRoutine(RoutineData routineData) {
        try {
            ResultSet routineResult = executeQuery("Select routineID FROM Routine WHERE eName = " + routineData.routineID);
            if (!routineResult.next()) {
                String insert = "INSERT INTO ExeName,targetBodyPart, caloriesPerMinute, equipment)\n";
                insert += String.format("VALUES('%s', '%d', %d, %s)", routineData.routineID, routineData.routineName, routineData.workouts);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }
    public int addNewExercise(ExerciseData exerciseData) {
        try {
            ResultSet exerciseResult = executeQuery("Select exercise NAME FROM Exercise WHERE eName = " + exerciseData.name);
            if (!exerciseResult.next()) {
                String insert = "INSERT INTO Exercise(eName,targetBodyPart, caloriesPerMinute, equipment)\n";
                insert += String.format("VALUES('%s', '%d', %d, %s)", exerciseData.name, exerciseData.targetBodyPart, exerciseData.caloriesPerMinute, exerciseData.equipment);
                executeUpdate(insert);
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }



    public int addNewSession(String sessionID, String userName, SessionData sessionData) {
         int nextID = Integer.parseInt(selectMax("Sessions", "sessionID")) + 1;
        try {
            ResultSet sessionResult = executeQuery("Select sessionID FROM Sessions WHERE sessionID = " + sessionID);
            if (!sessionResult.next()) {
                String insert = "INSERT INTO Sessions(sessionID, userName, routineID, startDate, endDate)\n";
                insert += String.format("VALUES('%s', '%s', %s, %t,%t)",sessionID, userName, sessionData.routine, sessionData.startDateTime, sessionData.endDateTime);
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