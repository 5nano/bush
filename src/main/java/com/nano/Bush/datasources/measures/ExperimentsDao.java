package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExperimentsDao {

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

    public List<Experiment> getExperiments() throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT Nombre,Descripcion,idEnsayo,idMezcla FROM experimento");
        List<Experiment> experiments = new ArrayList<>();
        while (resultSet.next()) {
            experiments.add(new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idMezcla")));
        }
        return experiments;
    }

    public Experiment getExperiment(String experimentId) throws SQLException {
        String query = "SELECT nombre,descripcion,idEnsayo,idMezcla FROM experimento WHERE idExperimento = '" + experimentId + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idMezcla"));
        }
        return null;
    }

    public void delete(String experimentName) throws SQLException {
        String query = "DELETE FROM experimento WHERE nombre ='" + experimentName + "'";
        preparedStatement = PostgresConnector.getInstance().getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(Experiment experiment) throws SQLException {
        this.delete(experiment.getName());
        this.insert(experiment);
    }
}
