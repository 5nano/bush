package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ExperimentsDao {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentsDao.class);
    private Statement statement;
    private PreparedStatement preparedStatement;

    public ExperimentsDao() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }


    public void insert(Experiment experiment) throws SQLException {
        preparedStatement = PostgresConnector.getInstance().getPreparedStatementFor("INSERT INTO experimento VALUES (default, ?, ?)");
        preparedStatement.setString(1, experiment.getName());
        preparedStatement.setString(2, experiment.getDescription());
        preparedStatement.executeUpdate();
    }

    public ResultSet getExperiments() throws SQLException {
        return statement.executeQuery("SELECT Nombre,Descripcion FROM experimento");

    }

    public Experiment getExperiment(String experimentId) throws SQLException {
        String query = "SELECT nombre,descripcion FROM experimento WHERE idExperimento = '" + experimentId + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"));
        }
        return null;
    }

    public void deleteExperiment(Experiment experiment) throws SQLException {
        String query = "DELETE FROM ensayos WHERE nombre ='" + experiment.getName() + "'";
        preparedStatement = PostgresConnector.getInstance().getPreparedStatementFor(query);
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
