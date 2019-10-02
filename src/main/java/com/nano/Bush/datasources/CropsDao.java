package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Crop;
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
public class CropsDao {

    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public void insert(Crop crop) throws SQLException {
        String query = "INSERT INTO  cultivo VALUES (default, ?, ?)";
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.setString(1, crop.getName());
        preparedStatement.setString(2, crop.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Crop> getCrops() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM cultivo");
        List<Crop> crops = new ArrayList<>();
        while (resultSet.next()) {
            crops.add(new Crop(resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return crops;
    }

    public void getCrop(Crop crop) throws SQLException {
        resultSet = statement.executeQuery("SELECT Nombre,Descripcion FROM cultivo WHERE nombre = '" + crop.getName() + "'");
    }

    public void delete(String cropName) throws SQLException {
        String query = "DELETE FROM cultivo WHERE nombre ='" + cropName + "'";
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void modify(Crop crop) throws SQLException {
        this.delete(crop.getName());
        this.insert(crop);
    }
}
