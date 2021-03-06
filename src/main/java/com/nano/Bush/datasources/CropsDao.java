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
import java.util.Optional;

@Service
public class CropsDao {

    @Autowired
    PostgresConnector postgresConnector;

    public void insert(Crop crop) throws SQLException {
        String query = "INSERT INTO  cultivo VALUES (default, ?, ?)";
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.setString(1, crop.getName());
        preparedStatement.setString(2, crop.getDescription());
        preparedStatement.executeUpdate();
    }

    public List<Crop> getCrops() throws SQLException {
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT * FROM cultivo");
        List<Crop> crops = new ArrayList<>();
        while (resultSet.next()) {
            crops.add(new Crop(Optional.of(resultSet.getInt("idCultivo")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return crops;
    }

    public void getCrop(Crop crop) throws SQLException {
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT Nombre,Descripcion FROM cultivo WHERE nombre = '" + crop.getName() + "'");
    }

    public void delete(Integer idCrop) throws SQLException {
        String query = "DELETE FROM cultivo WHERE idCultivo = " + idCrop;
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(query);
        preparedStatement.executeUpdate();
    }

    public void update(Crop crop) throws SQLException {
        postgresConnector.update("cultivo", "nombre", crop.getName(), "idCultivo", crop.getIdCrop().get());
        postgresConnector.update("cultivo", "descripcion", crop.getDescription(), "idCultivo", crop.getIdCrop().get());
    }
}
