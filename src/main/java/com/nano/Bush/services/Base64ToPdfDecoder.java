package com.nano.Bush.services;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;
/**
 * Created by Matias Zeitune nov. 2019
 **/

@Service
class Base64ToPdfDecoder {

    public DataHandler decode(String b64) {
        byte[] decoder = Base64.getDecoder().decode(b64);
        DataSource dataSource = new ByteArrayDataSource(decoder, "application/pdf");
        return new DataHandler(dataSource);
    }
}
