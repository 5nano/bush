package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Mixture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MixturesDao {

    private static final Logger logger = LoggerFactory.getLogger(MixturesDao.class);
    private Statement statement;
    private ResultSet resultSet;

    public MixturesDao() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }

    public void insert(Mixture Mixture) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("INSERT INTO mezcla VALUES (default, ?, ?)");
        preparedStatement.setString(1, Mixture.getName());
        preparedStatement.setString(2, Mixture.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Mixture> getMixtures() throws SQLException {
        resultSet = statement.executeQuery("SELECT nombre,descripcion FROM mezcla");
        List<Mixture> mixtures = new ArrayList<>();
        while (resultSet.next()) {
            mixtures.add(new Mixture(resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return mixtures;
    }

    public void getMixture(Mixture Mixture) throws SQLException {
        resultSet = statement.executeQuery("SELECT Nombre,Descripcion FROM mezcla WHERE nombre = '" + Mixture.getName() + "'");
    }

    public void deleteMixture(Mixture Mixture) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("DELETE FROM mezcla WHERE nombre ='" + Mixture.getName() + "'");
        preparedStatement.executeUpdate();
    }

}
