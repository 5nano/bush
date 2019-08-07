package com.nano.Bush.conectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class PostgresConnector {

    private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);

    private final String host = "ec2-174-129-226-234.compute-1.amazonaws.com";
    private final String port = "5432";
    private final String databasePath = "/d9gojcvt5uikag";
    private static PostgresConnector postgresConnector = new PostgresConnector();

    public static PostgresConnector getInstance() {
        return postgresConnector;
    }


    public Connection getConnection() {

        String user = "ylxgnzcpuvjkwr";
        String password = "dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d";
        String ssl = "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        String dbUrl = "jdbc:postgresql://" + host + ':' + port + databasePath + ssl;
        Connection connection = null;

        try {
            logger.info("Conectando a postgres");
            connection = DriverManager.getConnection(dbUrl, user, password);

        } catch (SQLException e) {
            logger.error("Error al conectarse con Postgress : " + e);
        }
        return connection;
    }

}


/**
 * Host
 * ec2-174-129-226-234.compute-1.amazonaws.com
 * Database
 * d9gojcvt5uikag
 * User
 * ylxgnzcpuvjkwr
 * Port
 * 5432
 * Password
 * dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d
 * URI
 * postgres://ylxgnzcpuvjkwr:dfa5fe2f24238710cf1f31b963f899f7137635c59222c0b947e29ad99dd1a15d@ec2-174-129-226-234.compute-1.amazonaws.com:5432/d9gojcvt5uikag
 * Heroku CLI
 * heroku pg:psql postgresql-lively-25299 --app relational5nano
 */
