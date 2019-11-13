package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Mixture;
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
public class MixturesDao {

    private static final Logger logger = LoggerFactory.getLogger(MixturesDao.class);
    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public void insert(Mixture Mixture) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO mezcla VALUES (default, ?, ?)");
        preparedStatement.setString(1, Mixture.getName());
        preparedStatement.setString(2, Mixture.getDescription());
        preparedStatement.executeUpdate();
    }

    public Optional<Mixture> getMixture(Integer mixtureId) {
        try {
            resultSet = statement.executeQuery("SELECT idmezcla,nombre,descripcion FROM mezcla WHERE idmezcla = " + mixtureId);
            while (resultSet.next()) {
                return Optional.of(new Mixture(Optional.of(resultSet.getInt("idmezcla")),
                        resultSet.getString("nombre"), resultSet.getString("descripcion")));
            }
        } catch (SQLException sqe) {
            logger.error("Unexpected  error with mixture " + mixtureId, sqe);
        }
        return Optional.empty();
    }

    public List<Mixture> getMixtures() throws SQLException {
        resultSet = statement.executeQuery("SELECT idmezcla,nombre,descripcion FROM mezcla");
        List<Mixture> mixtures = new ArrayList<>();
        while (resultSet.next()) {
            mixtures.add(new Mixture(Optional.of(resultSet.getInt("idmezcla")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return mixtures;
    }

    public void delete(Integer mixtureId) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("DELETE FROM mezcla WHERE idmezcla = " + mixtureId);
        preparedStatement.executeUpdate();
    }

    public void update(Mixture mixture) throws SQLException {
        postgresConnector.update("mezcla", "nombre", mixture.getName(), "idmezcla", mixture.getIdMixture().get());
        postgresConnector.update("mezcla", "descripcion", mixture.getDescription(), "idmezcla", mixture.getIdMixture().get());
    }

}
