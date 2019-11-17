package com.nano.Bush.conectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class PostgresConnector {

    private static final Logger logger = LoggerFactory.getLogger(PostgresConnector.class);

    private static volatile Connection connection;

    private static final String user = "ylxgnzcpuvjkwr";
    private static final String password = "dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d";
    private static final String ssl = "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String host = "ec2-174-129-226-234.compute-1.amazonaws.com";
    private static final String port = "5432";
    private static final String databasePath = "/d9gojcvt5uikag";
    private static final String dbUrl = "jdbc:postgresql://" + host + ':' + port + databasePath + ssl;

    @PostConstruct
    public void init() {
        try {
            logger.info("Conectando a postgres");
            connection = DriverManager.getConnection(dbUrl, user, password);
            this.validateConnection();
        } catch (SQLException e) {
            logger.error("Error al conectarse con Postgress : " + e);
        }
    }

    public synchronized Connection getConnection() {
        try {
            if(connection.isClosed()) {
                logger.info("Connection is closed getting new one");
                connection = DriverManager.getConnection(dbUrl, user, password);
            }
        } catch (SQLException e) {
            logger.error("Unexpected error", e);
        };
        return connection;
    }

    @Scheduled(fixedRate = 300000)
    public void validateConnection() throws SQLException {
        logger.info("Validating connection");
       this.getConnection().createStatement().executeQuery("Select 1;");
    }

    public PreparedStatement getPreparedStatementFor(String query) throws SQLException {
        return this.getConnection().prepareStatement(query);
    }

    public void update(String tableName, String fieldName, String value, String fieldNameUpdateWhere, Integer whereValue) throws SQLException {
        String query = "UPDATE " + tableName + " SET " + fieldName + " = '" + value + " ' WHERE " + fieldNameUpdateWhere + " = " + whereValue;
        PreparedStatement preparedStatement = this.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void update(String tableName, String fieldName, Double value, String fieldNameUpdateWhere, Integer whereValue) throws SQLException {
        String query = "UPDATE " + tableName + " SET " + fieldName + " = '" + value + " ' WHERE " + fieldNameUpdateWhere + " = " + whereValue;
        PreparedStatement preparedStatement = this.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }
}


/*
  Host
  ec2-174-129-226-234.compute-1.amazonaws.com
  Database
  d9gojcvt5uikag
  User
  ylxgnzcpuvjkwr
  Port
  5432
  Password
  dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d
  URI
  postgres://ylxgnzcpuvjkwr:dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d@ec2-174-129-226-234.compute-1.amazonaws.com:5432/d9gojcvt5uikag
  Heroku CLI
  heroku pg:psql postgresql-lively-25299 --app relational5nano
 */
