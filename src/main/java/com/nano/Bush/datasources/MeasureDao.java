package com.nano.Bush.datasources;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
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

    public List<MeasurePlant> selectMeasuresFrom(String idPlant, String idTest) {

        String query = "SELECT Measures FROM measures WHERE id_plant = " + idPlant + " AND id_test = " + idTest + "";
        ResultSet rs = CassandraConnector.getCassandraConection().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();
        rs.forEach(r -> putMeasure(rs, measuresPlants, r));

        for (long days = 0; days < measuresPlants.size(); days++) {
            measuresPlants.get(Integer.parseInt(String.valueOf(days))).setDay(LocalDate.now().plusDays(days)); //TODO: sacar el plus days cuando se inserte bien la fecha
        }

        return measuresPlants;
    }

    private void putMeasure(ResultSet rs, List<MeasurePlant> measuresPlants, Row r) {
        if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
            rs.fetchMoreResults();
        try {
            measuresPlants.add(new ObjectMapper().readValue(MeasureResponseMock.getMeasure(), MeasurePlant.class));
        } catch (IOException e) {
            throw new RuntimeException("JSON Parse error, exception: " + e);
        }
    }

    public String selectBase64ImageFrom(String idPlant, String idTest) {

        String query = "SELECT image FROM measures WHERE id_plant = " + idPlant + " AND id_test = " + idTest + "";//TODO: el nombre del campo
        System.out.println(CassandraConnector.getCassandraConection().execute(query).one().getString(0));

        return CassandraConnector.getCassandraConection().execute(query).one().getString(0);
    }
}