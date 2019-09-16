package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Agrochemical;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AgrochemicalsDao {


    private static final Logger logger = LoggerFactory.getLogger(AgrochemicalsDao.class);
    private PreparedStatement preparedStatement;
    private Statement statement;

    public AgrochemicalsDao() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }

    public void insert(Agrochemical agrochemical) throws SQLException {
        preparedStatement = PostgresConnector.getInstance().getPreparedStatementFor(("INSERT INTO  agroquimico VALUES (default, ?, ?)"));
        preparedStatement.setString(1, agrochemical.getName());
        preparedStatement.setString(2, agrochemical.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Agrochemical> getAgrochemicals() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM agroquimico");
        List<Agrochemical> agrochemicals = new ArrayList<>();
        while (resultSet.next()) {
            agrochemicals.add(new Agrochemical(resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return agrochemicals;
    }

    public void deleteAgrochemical(String agrochemicalName) throws SQLException {
        String query = "DELETE FROM agroquimico WHERE nombre ='" + agrochemicalName + "'";
        preparedStatement = PostgresConnector.getInstance().getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

}
