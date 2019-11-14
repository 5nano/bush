package com.nano.Bush.datasources.measures;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.conectors.CassandraConnector;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasuresDao {

    private static final Logger logger = LoggerFactory.getLogger(MeasuresDao.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<MeasurePlant> selectMeasuresFrom(Integer assayId, Integer experimentId) {

        String query = "SELECT time,measures,image FROM measures WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId + "";
        ResultSet rs = CassandraConnector.getConnection().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        for (Row row : rs) {
            try {

                String transformedText = row.getString("measures")
                        .replace(", 'datatype': \"<class 'list'>\",", ",")
                        .replace(", 'datatype': \"<class 'bool'>\",", ",")
                        .replace(", 'datatype': \"<class 'int'>\",", ",")
                        .replace(", 'datatype': \"<class 'float'>\",", ",")
                        .replace(", 'datatype': \"<class 'tuple'>\",", ",")
                        .replace("False", "false")
                        .replace("True", "true")
                        .replace("(", "\"")
                        .replace(")", "\"")
                        .replace(", 'datatype': \"<class 'float'>\",", ",")
                        .replaceAll("'", "\"");

                MeasurePlant measureConverted = mapper.readValue(transformedText, MeasurePlant.class);
                measureConverted.setDay(row.getTimestamp("time").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                measureConverted.setDayWithHour(row.getTimestamp("time").toInstant()); //TODO: fijarse el timestamp del cassandra vs aca en ARG
                measureConverted.setImage("http://35.188.202.169:8080" + row.getString("image"));
                measuresPlants.add(measureConverted);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return measuresPlants;
    }

    public List<String> getDatePicturesByAssayAndExperiment(Integer assayId, Integer experimentId) {
        String query = "SELECT time FROM measures WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId;

        ResultSet rs = CassandraConnector.getConnection().execute(query);

        List<String> dates = new ArrayList<>();
        for (Row r : rs) {
            if (r != null) {
                dates.add(r.getTimestamp("time").toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            }
        }
        return dates;
    }

    public void deleteExperiment(Integer assayId, Integer experimentId) {
        String query = "delete FROM measures WHERE id_experiment = " + experimentId + " AND id_assay = " + assayId;
        CassandraConnector.getConnection().execute(query);
    }

}
