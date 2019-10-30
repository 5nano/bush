package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.AssayStatesEnum;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssaysDao {

    private static final Logger logger = LoggerFactory.getLogger(AssaysDao.class);
    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public Integer insert(Assay Assay) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO ensayo (idEnsayo,idCultivo,nombre,descripcion,idUserCreador,estado,creado,idcompania) " +
                        "VALUES (default, ?, ?,?,?,?,?,?) RETURNING idEnsayo");
        preparedStatement.setInt(1, Assay.getIdCrop());
        preparedStatement.setString(2, Assay.getName());
        preparedStatement.setString(3, Assay.getDescription());
        preparedStatement.setInt(4, Assay.getIdUserCreator());
        preparedStatement.setString(5, AssayStatesEnum.ACTIVE.toString());
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(7, Assay.getIdCompany());

        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("idEnsayo");

    }

    public List<Assay> getAllAssays(Integer idCompany) throws SQLException {
        resultSet = statement.executeQuery("SELECT idEnsayo,nombre,descripcion,idCultivo,idUserCreador,estado,creado FROM ensayo where idcompania="+ idCompany);
        List<Assay> Assays = new ArrayList<>();
        while (resultSet.next()) {
            Assays.add(new Assay(Optional.of(resultSet.getInt("idEnsayo")), resultSet.getInt("idCultivo"), resultSet.getString("nombre"),
                    resultSet.getString("descripcion"), resultSet.getInt("idUserCreador"),
                    Optional.of(AssayStatesEnum.valueOf(resultSet.getString("estado"))),
                    Optional.of(resultSet.getTimestamp("creado"))));
        }
        return Assays;
    }

    public Optional<Assay> getAssay(Integer idAssay)  {

        try {
            resultSet = statement.executeQuery("SELECT idEnsayo,nombre,descripcion,idCultivo,idUserCreador,estado,creado FROM ensayo where idEnsayo = " + idAssay);
            while (resultSet.next()) {
                return Optional.of(new Assay(Optional.of(resultSet.getInt("idEnsayo")), resultSet.getInt("idCultivo"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getInt("idUserCreador"),
                        Optional.of(AssayStatesEnum.valueOf(resultSet.getString("estado"))),
                        Optional.of(resultSet.getTimestamp("creado"))));
            }
        } catch (SQLException sqe){
            logger.error("Unexpected error with assay " + idAssay,sqe);
        }

        return Optional.empty();
    }

    public List<Assay> getAssaysByState(Integer idCompany, AssayStatesEnum assayStatesEnum) throws SQLException {
        resultSet = statement.executeQuery("SELECT idEnsayo,nombre,descripcion,idCultivo,idUserCreador,estado,creado FROM ensayo where estado = '" + assayStatesEnum.toString() + "' and idcompania=" + idCompany);
        List<Assay> Assays = new ArrayList<>();
        while (resultSet.next()) {
            Assays.add(new Assay(Optional.of(resultSet.getInt("idEnsayo")), resultSet.getInt("idCultivo"), resultSet.getString("nombre"),
                    resultSet.getString("descripcion"), resultSet.getInt("idUserCreador"), Optional.of(AssayStatesEnum.valueOf(resultSet.getString("estado"))),
                    Optional.of(resultSet.getTimestamp("creado"))));
        }
        return Assays;
    }

    public List<Experiment> getExperimentsFromAssay(Integer assayId) throws SQLException {
        resultSet = statement.executeQuery("SELECT idExperimento,nombre,descripcion,idEnsayo,idTratamiento FROM experimento WHERE idEnsayo = '" + assayId + "'");
        List<Experiment> experiments = new ArrayList<>();
        while (resultSet.next()) {
            experiments.add(new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idTratamiento"), Optional.of(resultSet.getInt("idExperimento"))));
        }
        return experiments;
    }

    public List<Experiment> getExperimentsFromTreatment(String treatmentId) throws SQLException {
        resultSet = statement.executeQuery("SELECT idExperimento,nombre,descripcion,idEnsayo,idTratamiento FROM experimento WHERE idTratamiento = '" + treatmentId + "'");
        List<Experiment> experiments = new ArrayList<>();
        while (resultSet.next()) {
            experiments.add(new Experiment(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                    resultSet.getInt("idEnsayo"), resultSet.getInt("idTratamiento"), Optional.of(resultSet.getInt("idExperimento"))));
        }
        return experiments;
    }

    public void delete(Integer assayId) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor("DELETE FROM ensayo WHERE idEnsayo = " + assayId);
        preparedStatement.executeUpdate();
    }

    public void update(Assay assay) throws SQLException {
        postgresConnector.update("ensayo", "nombre", assay.getName(), "idEnsayo", assay.getIdAssay().get());
        postgresConnector.update("ensayo", "descripcion", assay.getDescription(), "idEnsayo", assay.getIdAssay().get());
    }

    public void archiveAssay(Integer idAssay) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("update ensayo set estado ='" + AssayStatesEnum.ARCHIVED.toString() + "' where idEnsayo = '"+idAssay+"'");
        preparedStatement.executeUpdate();
    }

    public void activeAssay(Integer idAssay) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("update ensayo set estado ='" + AssayStatesEnum.ACTIVE.toString() + "' where idEnsayo = '"+idAssay+"'");
        preparedStatement.executeUpdate();
    }

    public void finishAssay(Integer idAssay, Integer stars, String comments) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("update ensayo set estado ='" + AssayStatesEnum.FINISHED.toString() + "' where idEnsayo = '"+idAssay+"'");
        preparedStatement.executeUpdate();

        preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO ensayoTerminado (idEnsayoTerminado,idEnsayo,conclusiones,estrellas) VALUES (default, ?, ?,?)");
        preparedStatement.setInt(1, idAssay);
        preparedStatement.setString(2, comments);
        preparedStatement.setInt(3, stars);

        preparedStatement.executeUpdate();
    }
}
