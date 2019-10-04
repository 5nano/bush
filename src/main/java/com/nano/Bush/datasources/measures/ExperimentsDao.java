package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class ExperimentsDao {

    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;
    private PreparedStatement preparedStatement;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }


    public void insert(Experiment experiment) throws SQLException {
        preparedStatement = postgresConnector.getPreparedStatementFor("INSERT INTO experimento VALUES (default, ?, ?)");
        preparedStatement.setString(1, experiment.getName());
        preparedStatement.setString(2, experiment.getDescription());
        preparedStatement.executeUpdate();
    }

    public Experiment getExperiment(String experimentId) throws SQLException {
        String query = "SELECT nombre,descripcion,idEnsayo,idTratamiento FROM experimento WHERE idExperimento = '" + experimentId + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idTratamiento"), Integer.parseInt(experimentId));
        }
        return null;
    }

    public void delete(String experimentName) throws SQLException {
        String query = "DELETE FROM experimento WHERE nombre ='" + experimentName + "'";
        preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(Experiment experiment) throws SQLException {
        this.delete(experiment.getName());
        this.insert(experiment);
    }
}
