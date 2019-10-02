package com.nano.Bush.services.bulmapsaur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nano.Bush.model.bulmapsaur.BulmapsaurPayload;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

@Service
public class BulmapsaurService {
  private static final Logger logger = LoggerFactory.getLogger(BulmapsaurService.class);
  private final CloseableHttpClient client = HttpClients.createDefault();
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String bulmapsaurUrl = "http://35.188.202.169:8443/bulmapsaur/api/images";

  public String doPost(BulmapsaurPayload payload) throws Exception {
    try {
      HttpPost post = new HttpPost(URI.create(bulmapsaurUrl));
      StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(payload));
      post.setEntity(stringEntity);

      HttpResponse response = client.execute(post);

      logger.info("Bulmapsaur status code {}", response.getStatusLine().getStatusCode());

      BufferedReader rd = new BufferedReader(
              new InputStreamReader(response.getEntity().getContent()));

      StringBuffer result = new StringBuffer();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      return result.toString();
    } finally {
      client.close();
    }

  }


}
