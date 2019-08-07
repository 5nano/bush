package com.nano.Bush.datasources;

import com.nano.Bush.conectors.MySqlConnector;
import com.nano.Bush.model.Crop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CropDao {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlConnector.class);
    private PreparedStatement preparedStatement;
    private Statement statement;
    private Connection connector;
    private ResultSet resultSet;

    public CropDao(Connection connector) {
        try {
            this.statement = connector.createStatement();
        } catch (SQLException e) {
            LOG.error("Error al conectarse a Postgress :" + e);
        }
        this.connector = connector;
    }

    public void insertCrop(Crop crop) throws SQLException {
        preparedStatement = connector.prepareStatement("INSERT INTO  cultivos VALUES (default, ?, ?)");
        preparedStatement.setString(1, crop.getName());
        preparedStatement.setString(2, crop.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<String> getCrops() throws SQLException {
        resultSet = statement.executeQuery("SELECT USER FROM USER");
        List<String> crops = new ArrayList<>();
        while (resultSet.next()) {
            crops.add(resultSet.getString("user"));
        }
        return crops;
    }

    public void getCrop(Crop crop) throws SQLException {
        resultSet = statement.executeQuery("SELECT Nombre,Descripcion FROM cultivos WHERE nombre = '" + crop.getName() + "'");
    }

    public void deleteCrop(Crop crop) throws SQLException {
        preparedStatement = connector.prepareStatement("DELETE FROM cultivos WHERE nombre ='" + crop.getName() + "'");
        preparedStatement.executeUpdate();
    }

}
