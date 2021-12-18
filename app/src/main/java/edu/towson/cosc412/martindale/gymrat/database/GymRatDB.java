package edu.towson.cosc412.martindale.gymrat.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class GymRatDB {
    public static final String host = "gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com";
    public static final int port = 3306;
    public static final String dbName = "gymratDB";
    public static final String dbUser = "admin";
    public static final String dbPassword = "12345678";

    public static GymRatDB instance;

    Connection connection;
    Statement statement;

    private GymRatDB() {
        try {
            String url = String.format(Locale.ENGLISH, "jdbc:mysql://%s:%d/%s", host, port, dbName);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GymRatDB getInstance() {
        if (instance == null) {
            instance = new GymRatDB();
        }
        return instance;
    }

    public void addNewUser(UserData userData) {

    }

    public int addNewExercise(ExerciseData exerciseData) {
        try {
            ResultSet exerciseResult = executeQuery("Select eNAME FROM Exercise WHERE eName = " + exerciseData.name);
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
                insert += String.format("VALUES('%s', '%s', %s, %t,%t)",sessionID, userName, sessionData.routine, sessionData.startDate, sessionData.endDate);
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