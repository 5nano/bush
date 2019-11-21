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
import java.util.Optional;

@Service
public class UsersDao {

    private static final Logger logger = LoggerFactory.getLogger(UsersDao.class);
    @Autowired
    private PostgresConnector postgresConnector;

    public void insert(User user) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO usuario VALUES (default,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setInt(1, user.getCompanyId());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getUsername());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setBoolean(8, true);
        preparedStatement.setString(9, user.getEmail());

        preparedStatement.executeUpdate();
    }

    public List<User> getUsers() throws SQLException {
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT IdCompania,Usuario,Nombre,Apellido,Password,Email,idUsuario FROM Usuario");
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(resultSet.getString("Usuario"), resultSet.getString("Nombre"),
                    resultSet.getString("Apellido"), resultSet.getString("Password"),
                    resultSet.getString("Email"), resultSet.getInt("IdCompania"), Optional.of(resultSet.getInt("idUsuario"))));
        }
        return users;
    }

    public Option<User> getUserByUsername(String username) {
        try {
            ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT IdCompania,Usuario,Nombre,Apellido,Password,Email,idUsuario FROM Usuario WHERE Usuario ='" + username + "'");
            while (resultSet.next()) {
                return Option.of(new User(resultSet.getString("Usuario"), resultSet.getString("Nombre"),
                        resultSet.getString("Apellido"), resultSet.getString("Password"),
                        resultSet.getString("Email"), resultSet.getInt("IdCompania"), Optional.of(resultSet.getInt("idUsuario"))));
            }
            resultSet.close();
        } catch (Exception e) {
            logger.error("Unexpected error executing query", e);
        }

        return Option.none();
    }

    public Option<User> getUserById(Integer id) {
        try {
            ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT IdCompania,Usuario,Nombre,Apellido,Password,Email,idUsuario FROM Usuario WHERE idUsuario =" + id);
            while (resultSet.next()) {
                return Option.of(new User(resultSet.getString("Usuario"), resultSet.getString("Nombre"),
                        resultSet.getString("Apellido"), resultSet.getString("Password"),
                        resultSet.getString("Email"), resultSet.getInt("IdCompania"), Optional.of(resultSet.getInt("idUsuario"))));
            }
            resultSet.close();
        } catch (Exception e) {
            logger.error("Unexpected error executing query", e);
        }

        return Option.none();
    }


    public void delete(Integer userId) throws SQLException {
        String query = "DELETE FROM usuario WHERE idUsuario = " + userId;
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(User user) throws SQLException {

        postgresConnector.update("usuario", "nombre", user.getFirstName(), "idUsuario", user.getUserId().get());
        postgresConnector.update("usario", "apellido", user.getLastName(), "idUsuario", user.getUserId().get());
        postgresConnector.update("usuario", "usuario", user.getUsername(), "idUsuario", user.getUserId().get());
        postgresConnector.update("usuario", "password", user.getPassword(), "idUsuario", user.getUserId().get());
        postgresConnector.update("usuario", "email", user.getEmail(), "idUsuario", user.getUserId().get());

    }
}
