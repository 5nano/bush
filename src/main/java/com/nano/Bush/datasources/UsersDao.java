package com.nano.Bush.datasources;

import com.nano.Bush.conectors.MySqlConnector;
import com.nano.Bush.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    private static final Logger logger = LoggerFactory.getLogger(MySqlConnector.class);
    private Statement statement;
    private Connection connector;

    public UsersDao(Connection connector) {
        try {
            setStatement(connector.createStatement());
        } catch (SQLException e) {
            logger.error("Error al conectarse a Postgress :" + e);
        }
        this.connector = connector;
    }

    public void insertUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connector.prepareStatement("INSERT INTO  usuario VALUES (default,1, ?, ?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setBoolean(7, true);

        preparedStatement.executeUpdate();
    }

    public List<User> getUsers() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT Usuario,Nombre,Apellido,Password FROM Usuario");
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(resultSet.getString("Usuario"), resultSet.getString("Nombre"),
                    resultSet.getString("Apellido"), resultSet.getString("Password")));
        }
        return users;
    }


    private void setStatement(Statement statement) {
        this.statement = statement;
    }
}
