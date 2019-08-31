package com.nano.Bush.datasources;

import com.datastax.driver.core.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.Mocks.MeasureResponseMock;
import com.nano.Bush.conectors.CassandraConnector;
import com.nano.Bush.model.MeasurePlant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasureDao {

    private static final Logger logger = LoggerFactory.getLogger(MeasureDao.class);

    public List<MeasurePlant> selectMeasuresFrom(String assayId, String experimentId) {

        String query = "SELECT Measures FROM measures WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId + "";
        ResultSet rs = CassandraConnector.getCassandraConection().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();
        rs.forEach(r -> putMeasure(rs, measuresPlants));

        for (long days = 0; days < measuresPlants.size(); days++) {
            measuresPlants.get(Integer.parseInt(String.valueOf(days))).setDay(LocalDate.now().plusDays(days)); //TODO: sacar el plus days cuando se inserte bien la fecha
        }

        return measuresPlants;
    }

    private void putMeasure(ResultSet rs, List<MeasurePlant> measuresPlants) {
        if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
            rs.fetchMoreResults();
        try {
            measuresPlants.add(new ObjectMapper().readValue(MeasureResponseMock.getMeasure(), MeasurePlant.class));
        } catch (IOException e) {
            throw new RuntimeException("JSON Parse error, exception: " + e);
        }
    }

    public List<String> selectBase64ImageFrom(String experimentId, String assayId) {

        String query = "SELECT image FROM images WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId +
                " ALLOW FILTERING";

        ResultSet rs = CassandraConnector.getCassandraConection().execute(query);


        List<String> images = new ArrayList<>();

        if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
            rs.fetchMoreResults();
        rs.forEach(r -> images.add(r.getString(0)));

        return images;
    }
}