package com.nano.Bush.services;

import com.nano.Bush.conectors.PostgresConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class ValidationsService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationsService.class);
    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public Boolean isRepetead(String field, String table, String value) throws SQLException {

        String query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = '" + value + "'";
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet.next();

    }

    public Boolean isRepetead(String field, String table, Integer value) throws SQLException {

        String query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = " + value;
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet.next();

    }


}
