package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Agrochemical;
import com.nano.Bush.model.PressureIndicator;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgrochemicalsDao {

    private static final Logger logger = LoggerFactory.getLogger(AgrochemicalsDao.class);
    @Autowired
    PostgresConnector postgresConnector;
    private PreparedStatement preparedStatement;
    private Statement statement;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public void insert(Agrochemical agrochemical) throws SQLException {
        preparedStatement = postgresConnector.getPreparedStatementFor(("INSERT INTO  agroquimico VALUES (default, ?, ?)"));
        preparedStatement.setString(1, agrochemical.getName());
        preparedStatement.setString(2, agrochemical.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Agrochemical> getAgrochemicals() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM agroquimico");
        List<Agrochemical> agrochemicals = new ArrayList<>();
        while (resultSet.next()) {
            agrochemicals.add(new Agrochemical(Optional.of(resultSet.getInt("idAgroquimico")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return agrochemicals;
    }

    public Optional<Agrochemical> getAgrochemical(Integer id) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agroquimico where idagroquimico=" + id);
            while (resultSet.next()) {
                return Optional.of(new Agrochemical(Optional.of(resultSet.getInt("idagroquimico")), resultSet.getString("nombre"),
                        resultSet.getString("descripcion")));
            }
        } catch (SQLException sqe){
            logger.error("Unexpected error with idAgrochemical " + id, sqe);
        }

        return Optional.empty();
    }

    public void delete(Integer agrochemicalId) throws SQLException {
        String query = "DELETE FROM agroquimico WHERE idAgroquimico = " + agrochemicalId;
        preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public List<PressureIndicator> getAgrochemicalWithPressureFrom(Integer treatmentId) throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT t.idMezcla, a.nombre, t.presion FROM tratamiento AS t " +
                "JOIN mezclaAgroquimico AS ma ON t.idMezcla = ma.idMezcla " +
                "JOIN agroquimico AS a ON ma.idAgroquimico = a.idAgroquimico WHERE t.idTratamiento = " + treatmentId);

        List<PressureIndicator> pressureIndicators = new ArrayList<>();

        while (resultSet.next()) {
            pressureIndicators.add(new PressureIndicator(resultSet.getFloat("presion"), resultSet.getString("nombre"),
                    new Tuple2<>(0, 1200), resultSet.getInt("idMezcla")));
        }

        return pressureIndicators;
    }

    public List<Tuple3<String, String, Long>> getAllAgrochemicalsUsed() throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT mezclaAgro.nombreMezcla,mezclaAgro.nombreAgroquimico,COUNT(*) AS cantUsada" +
                " FROM ( SELECT ma.idAgroquimico, ma.idMezcla,m.nombre AS nombreMezcla,a.nombre AS nombreAgroquimico " +
                " FROM mezclaAgroquimico AS ma " +
                " JOIN agroquimico AS a ON ma.idAgroquimico = a.idAgroquimico " +
                " JOIN mezcla AS m ON ma.idMezcla = m.idMezcla) AS mezclaAgro " +
                " GROUP BY mezclaAgro.nombreMezcla,mezclaAgro.nombreAgroquimico"
        );

        List<Tuple3<String, String, Long>> agroWithMixture = new ArrayList<>();

        while (resultSet.next()) {
            agroWithMixture.add(new Tuple3<>(resultSet.getString("nombreMezcla"), resultSet.getString("nombreAgroquimico"), resultSet.getLong("cantUsada")));
        }

        return agroWithMixture;
    }

    public void update(Agrochemical agrochemical) throws SQLException {
        postgresConnector.update("agroquimico", "nombre", agrochemical.getName(), "idAgroquimico", agrochemical.getIdAgrochemical().get());
        postgresConnector.update("agroquimico", "descripcion", agrochemical.getDescription(), "idAgroquimico", agrochemical.getIdAgrochemical().get());
    }

}
