package com.nano.Bush.datasources;

import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ExperimentDao {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentDao.class);
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection;

    public ExperimentDao(Connection connection) {
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("Error al conectarse a Postgress :" + e);
        }
        this.connection = connection;
    }


    public void insertExperiment(Experiment experiment) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO experimento VALUES (default, ?, ?)");
        preparedStatement.setString(1, experiment.getName());
        preparedStatement.setString(2, experiment.getDescription());
        preparedStatement.executeUpdate();
    }

    public ResultSet getExperiments() throws SQLException {
        return statement.executeQuery("SELECT Nombre,Descripcion FROM experimento");

    }

    public Experiment getExperiment(String experimentId) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT nombre,descripcion FROM experimento WHERE idExperimento = '" + experimentId + "'");
        if (resultSet.next()) {
            return new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"));
        }
        return null;
    }

    public void deleteExperiment(Experiment experiment) throws SQLException {
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
