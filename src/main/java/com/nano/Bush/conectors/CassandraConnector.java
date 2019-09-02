package com.nano.Bush.conectors;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CassandraConnector {

    private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);
    private static Session session;

    public CassandraConnector() {
    }

    public static Session getConnection() {

        logger.info("Connect to Cassandra, host {}", "104.197.222.72");

        if (session == null) {
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

        return session;
    }

}
