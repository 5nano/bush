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
import java.util.Optional;

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


    public Integer insert(Experiment experiment) throws SQLException {
        preparedStatement = postgresConnector.getPreparedStatementFor("INSERT INTO experimento (idExperimento,idEnsayo,idTratamiento,nombre,descripcion) VALUES (default,?, ?, ?, ?) RETURNING idExperimento");
        preparedStatement.setInt(1, experiment.getAssayId());
        preparedStatement.setInt(2, experiment.getTreatmentId());
        preparedStatement.setString(3, experiment.getName());
        preparedStatement.setString(4, experiment.getDescription());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("idExperimento");
    }

    public Experiment getExperiment(Integer experimentId) throws SQLException {
        String query = "SELECT nombre,descripcion,idEnsayo,idTratamiento FROM experimento WHERE idExperimento = '" + experimentId + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idTratamiento"), Optional.of(experimentId));
        }
        return null;
    }

    public void delete(Experiment experiment) throws SQLException {
        String query = "DELETE FROM experimento WHERE idExperimento = " + experiment.getExperimentId();
        preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void update(Experiment experiment) throws SQLException {
        postgresConnector.update("experimento", "nombre", experiment.getName(), "idExperimento", experiment.getExperimentId().get());
        postgresConnector.update("experimento", "descripcion", experiment.getDescription(), "idExperimento", experiment.getExperimentId().get());
    }
}
