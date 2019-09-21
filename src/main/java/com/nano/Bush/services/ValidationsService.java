package com.nano.Bush.services;

import com.nano.Bush.conectors.PostgresConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidationsService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationsService.class);
    private Statement statement;

    public ValidationsService() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }

    public Boolean isRepetead(String field, String table, String value) throws SQLException {

        String query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = '" + value + "'";
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet.next();

    }


}
