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
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GraphicsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicsDao.class);

    /*
    public List<Test> getComparativeGraphicInfo(String crop) {

        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM PRUEBAS WHERE CULTIVO = '" + crop + "'";
        List<Test> tests = new ArrayList<>();

        try (PreparedStatement preparedStatement = PostgresConnector.getPreparedStatement(sqlQuery)) {

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultSet = preparedStatement.getResultSet();
                tests.add(getTest(resultSet));
            }

        } catch (SQLException sqlException) {
            LOGGER.error("SQL State: %s\n%s", sqlException.getSQLState(), sqlException.getMessage());
        } catch (Exception exception) {
            LOGGER.error("Exception error: %s", exception.getMessage());
        }


        return tests;
    }

    private Test getTest(ResultSet resultSet) throws SQLException {
        Test test = new Test();
        test.setLabel(resultSet.getString("DIA"));
        test.setY(resultSet.getInt("AREA_FOLIAR"));
        return test;
    }*/

    public List<GraphicDto> getTestsInfo() {
        LOGGER.info("Me pegaron");
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClientBuilder.create().build();
        String url = "https://api.myjson.com/bins/1ejdh9";
        List graphicDtos = new ArrayList<>();

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

