package com.nano.Bush.datasources.measures;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Assay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssaysDao {

    private static final Logger logger = LoggerFactory.getLogger(AssaysDao.class);
    private Statement statement;
    private ResultSet resultSet;

    public AssaysDao() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }

    public void insert(Assay Assay) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("INSERT INTO ensayo VALUES (default, ?, ?,?,?)");
        preparedStatement.setInt(1, Assay.getIdCrop());
        preparedStatement.setString(2, Assay.getName());
        preparedStatement.setString(3, Assay.getDescription());
        preparedStatement.setInt(4, Assay.getIdUserCreator());

        preparedStatement.executeUpdate();
    }

    public List<Assay> getAssays() throws SQLException {
        resultSet = statement.executeQuery("SELECT nombre,descripcion,idEnsayo,idUserCreador FROM ensayo");
        List<Assay> Assays = new ArrayList<>();
        while (resultSet.next()) {
            Assays.add(new Assay(resultSet.getInt("idEnsayo"), resultSet.getString("nombre"),
                    resultSet.getString("descripcion"), resultSet.getInt("idUserCreador")));
        }
        return Assays;
    }

    public void getAssay(Assay Assay) throws SQLException {
        String query = "SELECT Nombre,Descripcion,idCultivo,idUserCreador FROM mezcla WHERE nombre = '" + Assay.getName() + "'";
        resultSet = statement.executeQuery(query);
    }

    public void delete(String assayName) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("DELETE FROM ensayo WHERE nombre ='" + assayName + "'");
        preparedStatement.executeUpdate();
    }

    public void modify(Assay assay) throws SQLException {
        this.delete(assay.getName());
        this.insert(assay);
    }
}
