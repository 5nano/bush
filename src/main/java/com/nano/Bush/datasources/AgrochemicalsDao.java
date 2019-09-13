package com.nano.Bush.datasources;

import com.nano.Bush.model.Agrochemical;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgrochemicalsDao {


    private static final Logger logger = LoggerFactory.getLogger(AgrochemicalsDao.class);
    private PreparedStatement preparedStatement;
    private Statement statement;
    private Connection connector;

    public AgrochemicalsDao(Connection connector) {
        try {
            this.statement = connector.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectarse a Postgress :" + e);
        }
        this.connector = connector;
    }

    public void insertAgrochemical(Agrochemical agrochemical) throws SQLException {
        preparedStatement = connector.prepareStatement("INSERT INTO  agroquimico VALUES (default, ?, ?)");
        preparedStatement.setString(1, agrochemical.getName());
        preparedStatement.setString(2, agrochemical.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Agrochemical> getAgrochemicals() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM agroquimico");
        List<Agrochemical> agrochemicals = new ArrayList<>();
        while (resultSet.next()) {
            agrochemicals.add(new Agrochemical(resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return agrochemicals;
    }

    public void deleteAgrochemical(String agrochemicalName) throws SQLException {
        preparedStatement = connector.prepareStatement("DELETE FROM agroquimico WHERE nombre ='" + agrochemicalName + "'");
        preparedStatement.executeUpdate();
    }

}
