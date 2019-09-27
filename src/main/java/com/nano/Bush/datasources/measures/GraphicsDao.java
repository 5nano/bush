package com.nano.Bush.datasources.measures;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.measuresGraphics.GraphicDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GraphicsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicsDao.class);
    @Autowired PostgresConnector postgresConnector;

    public GraphicsDao() {
    }

    public Map<String, String> getExperimentsIds(String crop) {

        ResultSet resultSet;
        String idCrop = null;
        String idExperimentsQuery = "SELECT IDENSAYO FROM ENSAYO WHERE IDCULTIVO = 1";
        String idCropQuery = "SELECT IDCULTIVO FROM CULTIVO WHERE NOMBRE ='" + crop + "'";

        List<String> experimentId = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor(idCropQuery);
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                idCrop = resultSet.getString("IDCULTIVO");
            }
            preparedStatement = postgresConnector.getPreparedStatementFor(idExperimentsQuery + idCrop + "'");
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                experimentId.add(resultSet.getString("IDENSAYO"));
            }

        } catch (SQLException sqlException) {
            LOGGER.error("SQL State: " + sqlException.getSQLState() + " Message: " + sqlException.getMessage());
            throw new RuntimeException(sqlException);
        } catch (Exception exception) {
            LOGGER.error("Exception error: %s", exception.getMessage());
            throw new RuntimeException(exception);
        }

        Map<String, String> experimentsToCassandraQuery = new HashMap<>();

        String finalIdCrop = idCrop;

        experimentId.forEach(e -> experimentsToCassandraQuery.put(e, finalIdCrop));

        return experimentsToCassandraQuery;
    }

    public List<GraphicDto> getTestsInfo() {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClientBuilder.create().build();
        String url = "https://api.myjson.com/bins/1ejdh9";
        List<GraphicDto> graphicDtos = new ArrayList<>();

        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response;
            response = client.execute(request);
            graphicDtos = mapper.readValue(EntityUtils.toString(response.getEntity()), graphicDtos.getClass());
            return graphicDtos;

        } catch (JsonParseException e) {
            LOGGER.error("Parser JSON failed " + e);
        } catch (JsonMappingException e) {
            LOGGER.error("Mapping JSON Failed " + e);
        } catch (IOException e) {
            LOGGER.error("Error: " + e);
        }
        return graphicDtos;
    }

}

