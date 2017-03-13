package com.example.lennon;

import com.builtamont.cassandra.migration.CassandraMigration;
import com.builtamont.cassandra.migration.api.configuration.KeyspaceConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;

@SpringBootApplication
@EnableConfigurationProperties(CassandraProperties.class)
public class SpringBootCassandraMigrationApplication {

    private static final Logger logger = LoggerFactory
            .getLogger(SpringBootCassandraMigrationApplication.class);

    private final CassandraProperties properties;

    public SpringBootCassandraMigrationApplication(CassandraProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCassandraMigrationApplication.class, args);
    }

    @Bean
    InitializingBean migrationCassandra() {
        return () -> {
            String[] locations = { "classpath:data-migration" };
            logger.info("contactPoints: {}, port: {}, keyspaceName: {}, " +
                            "scriptLocation: {}",
                    properties.getContactPoints(), properties.getPort(),
                    properties.getKeyspaceName(),
                    locations);

            KeyspaceConfiguration keyspaceConfig = getKeyspaceConfiguration();
            CassandraMigration cm = getCassandraMigration(locations,
                    keyspaceConfig);
        };
    }

    @NotNull
    private KeyspaceConfiguration getKeyspaceConfiguration() {
        KeyspaceConfiguration keyspaceConfig = new KeyspaceConfiguration();
        keyspaceConfig.setName(properties.getKeyspaceName());
        keyspaceConfig.getClusterConfig().setContactpoints(properties
                .getContactPoints().split(","));
        keyspaceConfig.getClusterConfig().setPort(properties.getPort());
        return keyspaceConfig;
    }

    @NotNull
    private CassandraMigration getCassandraMigration(String[] locations,
            KeyspaceConfiguration keyspaceConfig) {
        CassandraMigration cm = new CassandraMigration();
        cm.setLocations(locations);
        cm.setKeyspaceConfig(keyspaceConfig);
        cm.migrate();
        return cm;
    }
}
