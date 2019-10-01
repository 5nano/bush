package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.User;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersDao {

    private static final Logger logger = LoggerFactory.getLogger(UsersDao.class);
    @Autowired
    private PostgresConnector postgresConnector;
    private Statement statement;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public void insert(User user) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO  usuario VALUES (default,1, ?, ?,?,?,?,?,?)");
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

    public Option<User> getUserByUsername(String username) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT Usuario,Nombre,Apellido,Password FROM Usuario where Usuario ='" + username + "'");
            while (resultSet.next()) {
                return Option.of(new User(resultSet.getString("Usuario"), resultSet.getString("Nombre"),
                        resultSet.getString("Apellido"), resultSet.getString("Password")));
            }
            resultSet.close();
        } catch (Exception e) {
            logger.error("Unexpected error executing query", e);
        }

        return Option.none();
    }

    public void delete(String username) throws SQLException {
        String query = "DELETE FROM usuario WHERE usuario ='" + username + "'";
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(User user) throws SQLException {
        this.delete(user.getUsername());
        this.insert(user);
    }
}
