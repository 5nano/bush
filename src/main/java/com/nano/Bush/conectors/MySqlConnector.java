package com.nano.Bush.conectors;


import java.sql.*;

public class MySqlConnector {

    private final String user = "ROOT";
    private final String pass = "ROOT";
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    MySqlConnector() {
        try {
            this.connect();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con MySql, exception: " + e);
        }
    }

    public void connect() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?user=" + user + "&password=" + pass);
            //TODO: aca falta la url, el user y el pass
            statement = connect.createStatement();

        } catch (Exception e) {
            throw new Exception("No se pudo conectar con MySql, exception: " + e);
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }

    private void close() {
        try {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (connect != null)
                connect.close();

        } catch (Exception e) {
            throw new RuntimeException("No se pudo closear MySql, exception " + e);
        }
    }

}
