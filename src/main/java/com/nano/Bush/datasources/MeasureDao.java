package com.nano.Bush.datasources;

import com.datastax.driver.core.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.conectors.CassandraConnector;
import com.nano.Bush.model.LeafAreaPlantDto;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureDao.class);

    public List<MeasurePlant> selectMeasuresFrom(String idPlant, String idTest) {

        String query = "SELECT * FROM measures WHERE id_plant = " + idPlant + " AND id_test = " + idTest + "";
        ResultSet rs = CassandraConnector.getCassandraConection().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();

        rs.forEach(r -> {
            if (rs.getAvailableWithoutFetching() == 100 && !rs.isFullyFetched())
                rs.fetchMoreResults(); // this is asynchronous
            try {
                measuresPlants.add(new MeasurePlant(new ObjectMapper().readValue(r.getString("measures"), LeafAreaPlantDto.class).getLeafArea()));
            } catch (IOException e) {
                throw new RuntimeException("JSON Parse error, exception: " + e);
            }
        });

        LocalDate date = LocalDate.now();

        for (long days = 0; days < measuresPlants.size(); days++) {
            measuresPlants.get(Integer.parseInt(String.valueOf(days))).setDay(date.plusDays(days));
        }

        return measuresPlants;
    }

}