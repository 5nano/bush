package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Assay;
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

    public void insert(Assay Assay) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO ensayo VALUES (default, ?, ?,?,?)");
        preparedStatement.setInt(1, Assay.getIdCrop());
        preparedStatement.setString(2, Assay.getName());
        preparedStatement.setString(3, Assay.getDescription());
        preparedStatement.setInt(4, Assay.getIdUserCreator());

        preparedStatement.executeUpdate();
    }

    public List<Assay> getAssays() throws SQLException {
        resultSet = statement.executeQuery("SELECT idEnsayo,nombre,descripcion,idCultivo,idUserCreador FROM ensayo");
        List<Assay> Assays = new ArrayList<>();
        while (resultSet.next()) {
            Assays.add(new Assay(resultSet.getInt("idEnsayo"), resultSet.getInt("idCultivo"),resultSet.getString("nombre"),
                    resultSet.getString("descripcion"), resultSet.getInt("idUserCreador")));
        }
        return Assays;
    }

    public List<Integer> getExperiments(String assayId) throws SQLException {
        resultSet = statement.executeQuery("SELECT idExperimento FROM experimento where idEnsayo = '" + assayId + "'");
        List<Integer> experiments = new ArrayList<>();
        while (resultSet.next()) {
            experiments.add(resultSet.getInt("idExperimento"));
        }
        return experiments;
    }

    public void getAssay(Assay Assay) throws SQLException {
        String query = "SELECT Nombre,Descripcion,idCultivo,idUserCreador FROM mezcla WHERE nombre = '" + Assay.getName() + "'";
        resultSet = statement.executeQuery(query);
    }

    public void delete(String assayName) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("DELETE FROM ensayo WHERE nombre ='" + assayName + "'");
        preparedStatement.executeUpdate();
    }

    public void modify(Assay assay) throws SQLException {
        this.delete(assay.getName());
        this.insert(assay);
    }
}
