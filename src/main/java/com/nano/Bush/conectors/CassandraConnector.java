package com.nano.Bush.conectors;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CassandraConnector {

    private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);

    public CassandraConnector() {
    }

    public static Session getCassandraConection() {

        logger.info("Connect to Cassandra, host {}" + "104.197.222.72");

        Cluster cluster = Cluster.builder()
                .addContactPoint("104.197.222.72")
                .withQueryOptions(new QueryOptions().setFetchSize(2000))
                .withoutJMXReporting()
                .build();

        cluster.getConfiguration().getQueryOptions().setFetchSize(2000);

        Statement statement = new SimpleStatement("USE nano");
        statement.setFetchSize(2000);

        Session session = cluster.newSession();

        session.execute("USE nano");

        return session;
    }

}
