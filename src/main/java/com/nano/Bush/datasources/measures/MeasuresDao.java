package com.nano.Bush.datasources.measures;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.conectors.CassandraConnector;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasuresDao {

    private static final Logger logger = LoggerFactory.getLogger(MeasuresDao.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<MeasurePlant> selectMeasuresFrom(String assayId, String experimentId) {

        String query = "SELECT measures FROM measures WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId + "";
        ResultSet rs = CassandraConnector.getConnection().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        for (Row row : rs) {
            try {

                String transformedText = row.getString("measures")
                        .replaceAll(", 'datatype': \"<class '.*.'>\",", ",")
                        .replace("False", "false")
                        .replace("True", "true")
                        .replace("(", "\"")
                        .replace(")", "\"")
                        .replaceAll("'", "\"");

                measuresPlants.add(mapper.readValue(transformedText, MeasurePlant.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        for (long days = 0; days < measuresPlants.size(); days++) {
            measuresPlants.get(Integer.parseInt(String.valueOf(days))).setDay(LocalDate.now().plusDays(days)); //TODO: sacar el plus days cuando se inserte bien la fecha
        }

        return measuresPlants;
    }

    private void putMeasure(Row r, ResultSet rs, List<MeasurePlant> measuresPlants) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
            rs.fetchMoreResults();
        //measuresPlants.add(mapper.readValue(r.getString('Measures'), MeasurePlant.class));
    }

    public List<String> selectBase64ImageFrom(String experimentId, String assayId) {

        String query = "SELECT image FROM images WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId +
                " ALLOW FILTERING";

        ResultSet rs = CassandraConnector.getConnection().execute(query);


        List<String> images = new ArrayList<>();

        if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
            rs.fetchMoreResults();
        rs.forEach(r -> images.add(r.getString(0)));

        return images;
    }
}
