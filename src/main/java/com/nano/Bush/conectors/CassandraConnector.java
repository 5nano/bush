package com.nano.Bush.conectors;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CassandraConnector {

    private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);
    private static Session session;

    @PostConstruct
    synchronized public void init(){
        logger.info("Connect to Cassandra, host {}", "104.197.222.72");

        Cluster cluster = Cluster.builder()
                .addContactPoint("104.197.222.72")
                .withQueryOptions(new QueryOptions().setFetchSize(2000))
                .withSocketOptions(new SocketOptions().setConnectTimeoutMillis(90000))
                .withoutJMXReporting()
                .build();

        cluster.getConfiguration().getQueryOptions().setFetchSize(2000);

        Statement statement = new SimpleStatement("USE nano");
        statement.setFetchSize(2000);

        session = cluster.newSession();
        session.execute("USE nano");

    }

    private CassandraConnector() {
    }

    public static Session getConnection() {
        return session;
    }

}
