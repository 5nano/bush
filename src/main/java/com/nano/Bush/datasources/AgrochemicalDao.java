package com.nano.Bush.datasources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgrochemicalDao {


    private static final Logger logger = LoggerFactory.getLogger(AgrochemicalDao.class);
    private PreparedStatement preparedStatement;
    private Statement statement;
    private Connection connector;

    public AgrochemicalDao(Connection connector) {
        try {
            this.statement = connector.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectarse a Postgress :" + e);
        }
        this.connector = connector;
    }

    public void insertAgrochemical(String agrochemicalName, String agrochemicalDescription) throws SQLException {
        preparedStatement = connector.prepareStatement("INSERT INTO  agroquimico VALUES (default, ?, ?)");
        preparedStatement.setString(1, agrochemicalName);
        preparedStatement.setString(2, agrochemicalDescription);
        preparedStatement.executeUpdate();
    }

    public List<String> getAgrochemicals() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM agroquimico");
        List<String> crops = new ArrayList<>();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("nombre"));
            crops.add(resultSet.getString("nombre"));
        }
        return crops;
    }

    public void deleteAgrochemical(String agrochemicalName) throws SQLException {
        preparedStatement = connector.prepareStatement("DELETE FROM agroquimico WHERE nombre ='" + agrochemicalName + "'");
        preparedStatement.executeUpdate();
    }

}
