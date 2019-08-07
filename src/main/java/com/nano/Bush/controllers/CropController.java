package com.nano.Bush.controllers;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.CropDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
public class CropController {


    @GetMapping(value = "/bush/getCrops")
    @ResponseBody
    public List<String> getComparativeInfoGraphic() throws SQLException {
        Connection postgresConection = PostgresConnector.getInstance().getConnection();
        CropDao cropDao = new CropDao(postgresConection);


        return cropDao.getCrops();
    }
}
