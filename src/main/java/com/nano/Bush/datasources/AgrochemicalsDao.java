package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Agrochemical;
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

    public void delete(String agrochemicalName) throws SQLException {
        String query = "DELETE FROM agroquimico WHERE nombre ='" + agrochemicalName + "'";
        preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(Agrochemical agrochemical) throws SQLException {
        this.delete(agrochemical.getName());
        this.insert(agrochemical);
    }

}
