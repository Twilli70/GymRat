package edu.towson.cosc412.martindale.gymrat;


import java.sql.*;
import java.util.ArrayList;

public class TritonDB {
    public static final String host = "triton";
    public static final int port = 3360;
    public static final String dbName = "twilli70db";
    public static final String dbUser = "twilli70";
    public static final String dbPassword = "COSC*3acm9";

    public static TritonDB instance;

    Connection connection;
    Statement statement;

    private TritonDB(){
        try{
            var url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TritonDB getInstance(){
        if (instance == null){
            instance = new TritonDB();
        }
        return instance;
    }

    public String selectMax(String table, String attribute){
        try{
            var result = executeQuery(String.format("SELECT MAX(%s) FROM %s", attribute, table));
            result.next();
            return result.getString(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void insertStringInto(String table, String[] columns, String[] values){
        try{
            if (columns.length != values.length){
                System.out.println("insertion amount does not match column amount");
                return;
            }

            var insertion = new StringBuilder();
            insertion.append("INSERT INTO " + table + "(");
            for(int i = 0; i < columns.length; i++){
                insertion.append(columns[i]);
                if (i < columns.length - 1){
                    insertion.append(",");
                }
            }
            insertion.append(")\n");

            insertion.append("VALUES(");
            for(int i = 0; i < values.length; i++){
                insertion.append("\"" + values[i] + "\"");
                if (i < values.length - 1){
                    insertion.append(",");
                }
            }
            insertion.append(");");
            System.out.println(insertion);
            statement.executeUpdate(insertion.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql){
        try{
            statement.executeUpdate(sql);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (connection != null){
            return statement.executeQuery(sql);
        }
        return null;
    }

    public String[] getResultColumns(ResultSet result){
        try{
            var resultMeta = result.getMetaData();
            var columnCount = resultMeta.getColumnCount();
            var columnNames = new String[columnCount];
            var rowEntries = new ArrayList<String[]>();

            for (int i = 1; i <= columnCount; i++){
                columnNames[i - 1] = resultMeta.getColumnName(i);
            }
            return columnNames;

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String[][] getResultRows(ResultSet result){
        try{
            var resultMeta = result.getMetaData();
            var columnCount = resultMeta.getColumnCount();
            var rowList = new ArrayList<String[]>();

            while(result.next()){
                var rowEntry = new String[columnCount];
                for (int i = 1; i <= columnCount; i++){
                    rowEntry[i - 1] = result.getString(i);
                }
                rowList.add(rowEntry);
            }
            if (rowList.size() > 0){
                var rows = new String[rowList.size()][rowList.get(0).length];
                for(int i = 0; i < rowList.size(); i++){
                    rows[i] = rowList.get(i);
                }
                return rows;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void printResult(ResultSet result){
        if (result != null){
            try {
                var resultMeta = result.getMetaData();
                var columns = resultMeta.getColumnCount();
                var rows = new ArrayList<String>();
                var format = new StringBuilder();

                result.beforeFirst();

                for (int i = 1; i <= columns; i++){
                    rows.add("[ " + resultMeta.getColumnName(i) + " ]");
                    format.append("%").append(i).append("$-35s");
                }

                System.out.format(format + "\n", rows.toArray());

                rows.clear();
                while(result.next()){
                    for (int i = 1; i <= columns; i++){
                        var columnValue = result.getString(i);
                        rows.add("| " + columnValue + " |");
                    }
                    System.out.format(format + "\n", rows.toArray());
                }

                // May be unnecessary, intended show sql errors in console
                var warning = result.getWarnings();
                while (warning != null) {
                    System.out.println(warning.getMessage());
                    warning = warning.getNextWarning();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}