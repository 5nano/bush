package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Treatment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Service
public class TreatmentsDao {
    private static final Logger logger = LoggerFactory.getLogger(TreatmentsDao.class);
    @Autowired
    PostgresConnector postgresConnector;
    @Autowired
    ExperimentsDao experimentsDao;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public List<Integer> insert(Treatment treatment) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("insert into tratamiento (idTratamiento, idEnsayo, idagroquimico, idmezcla,nombre,descripcion,presion) values (default, ?, ?, ?, ?, ?, ?) RETURNING idTratamiento");
        preparedStatement.setInt(1, treatment.getIdAssay());
        if (treatment.getIdAgrochemical() == null) {
            preparedStatement.setNull(2, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(2, treatment.getIdAgrochemical());
        }
        if (treatment.getIdAgrochemical() == null) {
            preparedStatement.setNull(3, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(3, treatment.getIdMixture());
        }
        preparedStatement.setString(4, treatment.getName());
        if (treatment.getDescription() == null) {
            preparedStatement.setNull(5, Types.VARBINARY);
        } else {
            preparedStatement.setString(5, treatment.getDescription());
        }
        if (treatment.getPressure() == null) {
            preparedStatement.setNull(6, Types.FLOAT);
        } else {
            preparedStatement.setDouble(6, treatment.getPressure().get());
        }


        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Integer idTreatment = resultSet.getInt("idTratamiento");

        List<Integer> experimentsIds = treatment.getExperimentsLength().map(experimentsLegth -> {
            List<Integer> ids = new ArrayList<>();
            for (Integer i = 0; i < experimentsLegth; i++) {
                try {
                    ids.add(experimentsDao.insert(new Experiment("-", "-", treatment.getIdAssay(), idTreatment, Optional.empty())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            return ids;
        }).get();

        return experimentsIds;

    }

    public void update(Treatment treatment) throws SQLException {
        postgresConnector.update("tratamiento", "nombre", treatment.getName(), "idTratamiento", treatment.getIdTreatment().get());
        postgresConnector.update("tratamiento", "descripcion", treatment.getDescription(), "idTratamiento", treatment.getIdTreatment().get());
        postgresConnector.update("tratamiento", "presion", treatment.getPressure().get(), "idTratamiento", treatment.getIdTreatment().get());

    }

    public List<Treatment> getTreatments(Integer assayId) throws SQLException {
        resultSet = statement.executeQuery("SELECT idTratamiento, idEnsayo, idagroquimico, idmezcla,nombre,descripcion,presion FROM tratamiento WHERE idEnsayo = '" + assayId + "'");
        List<Treatment> treatments = new ArrayList<>();
        while (resultSet.next()) {
            treatments.add(new Treatment(Optional.of(resultSet.getInt("idTratamiento")), Optional.empty(), resultSet.getInt("idEnsayo"),
                    resultSet.getInt("idagroquimico"),
                    resultSet.getInt("idmezcla"), resultSet.getString("nombre"), resultSet.getString("descripcion"), Optional.of(resultSet.getDouble("presion"))));
        }
        return treatments;
    }

    public List<Experiment> getExperiments(Integer idTreatment) throws SQLException {
        resultSet = statement.executeQuery("SELECT idExperimento,nombre,descripcion,idEnsayo,idTratamiento FROM experimento WHERE idTratamiento = '" + idTreatment + "'");
        List<Experiment> experiments = new ArrayList<>();
        while (resultSet.next()) {
            experiments.add(new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idTratamiento"), Optional.of(resultSet.getInt("idExperimento"))));
        }
        return experiments;
    }

    public Treatment getTreatment(Integer idTreatment) throws SQLException {
        String query = "SELECT idTratamiento, idEnsayo, idagroquimico, idmezcla,nombre,descripcion,presion FROM tratamiento WHERE idTratamiento = '" + idTreatment + "'";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Treatment(Optional.of(resultSet.getInt("idTratamiento")), Optional.empty(), resultSet.getInt("idEnsayo"),
                    resultSet.getInt("idagroquimico"),
                    resultSet.getInt("idmezcla"), resultSet.getString("nombre"), resultSet.getString("descripcion"), Optional.of(resultSet.getDouble("presion")));
        }
        return null;
    }

    public void delete(Integer idTreatment) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("DELETE FROM tratamiento WHERE idTratamiento = " + idTreatment);
        preparedStatement.executeUpdate();
    }

}
