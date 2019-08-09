package com.nano.Bush.datasources;

import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ExperimentDao {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentDao.class);
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Connection connection;

    ExperimentDao(Connection connection) {
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("Error al conectarse a Postgress :" + e);
        }
        this.connection = connection;
    }


    private void insertExperiment(Experiment experiment) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO ensayos VALUES (default, ?, ?)");
        preparedStatement.setString(1, experiment.getName());
        preparedStatement.setString(2, experiment.getDescription());
        preparedStatement.executeUpdate();
    }

    private void getExperiments() throws SQLException {
        resultSet = statement.executeQuery("SELECT Nombre,Descripcion FROM ensayos");
        writeResultSet(resultSet);
    }

    private void getExperiment(Experiment experiment) throws SQLException {
        resultSet = statement.executeQuery("SELECT Nombre,Descripcion FROM ensayos WHERE nombre = '" + experiment.getName() + "'");
        writeResultSet(resultSet);
    }

    private void deleteExperiment(Experiment experiment) throws SQLException {
        preparedStatement = connection.prepareStatement("DELETE FROM ensayos WHERE nombre ='" + experiment.getName() + "'");
        preparedStatement.executeUpdate();
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String name = resultSet.getString("Nombre");
            String description = resultSet.getString("Descripcion");
            logger.info(name + description);
        }
    }
}
