package com.nano.Bush.datasources;

import org.springframework.stereotype.Repository;

@Repository
public class MeasureDao {

    /*
    public List<MeasurePlant> selectMeasuresFrom(String crop) {

        CassandraConnector cassandraConnector = new CassandraConnector();
        cassandraConnector.connect("", 2);//TODO:nodo y puerto

        String TABLE_NAME = "measures";
        String query = "SELECT * FROM " + TABLE_NAME + "WHERE CULTIVO = '" + crop + "'";
        ResultSet rs = cassandraConnector.getSession().execute(query);

        List<MeasurePlant> measuresPlants = new ArrayList<>();

        rs.forEach(r -> {
            measuresPlants.add(new MeasurePlant(
                    r.getString("day"),
                    r.getString("witdh"),
                    r.getString("foliar-area")));
        });
        return measuresPlants;
    }*/

}
