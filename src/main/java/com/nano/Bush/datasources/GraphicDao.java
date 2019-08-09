package com.nano.Bush.datasources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.model.GraphicDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicDao.class);
    private Statement statement;

    public GraphicDao(Connection connector) {
        try {
            this.statement = connector.createStatement();
        } catch (SQLException e) {
            LOGGER.error("Error al conectarse a Postgress :" + e);
        }
    }

    public Map<String, String> getExperimentsIds(String crop) {

        ResultSet resultSet;
        String idCrop = null;
        String idExperimentsQuery = "SELECT ID_ENSAYO FROM ENSAYOS WHERE ID_CULTIVO = '";
        String idCropQuery = "SELECT ID_CULTIVO FROM CULTIVOS WHERE CULTIVO ='" + crop + "'";

        List<String> experimentId = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(idCropQuery);
            while (resultSet.next()) {
                idCrop = resultSet.getString("ID_CULTIVO");
            }
            resultSet = statement.executeQuery(idExperimentsQuery+idCrop+"'");
            while (resultSet.next()){
                experimentId.add(resultSet.getString("ID_ENSAYO"));
            }

        } catch (SQLException sqlException) {
            LOGGER.error("SQL State: %s\n%s", sqlException.getSQLState(), sqlException.getMessage());
        } catch (Exception exception) {
            LOGGER.error("Exception error: %s", exception.getMessage());
        }

        Map<String,String> experimentsToCassandraQuery  = new HashMap<>();

        String finalIdCrop = idCrop;

        experimentId.forEach(e->experimentsToCassandraQuery.put(e, finalIdCrop));

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

