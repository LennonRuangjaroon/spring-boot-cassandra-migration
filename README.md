# spring-boot-cassandra-migration
   Spring Boot migration the Apache Cassandra database using builtamont-oss/cassandra-migration

#### Import Spring Data for Apache Cassandra
[spring-data-cassandra](http://projects.spring.io/spring-data-cassandra/)

        <dependencies>
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-cassandra</artifactId>
                <version>1.5.1.RELEASE</version>
            </dependency>
        </dependencies>

#### Import builtamont-oss/cassandra-migration
[builtamont-oss/cassandra-migration](https://github.com/builtamont-oss/cassandra-migration)

        <dependency>
            <groupId>com.builtamont</groupId>
            <artifactId>cassandra-migration</artifactId>
            <version>0.10</version>
        </dependency>

#### Run Docker Cassandra
    $ docker run --name cassandra -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160   -d cassandra

#### Create cassandra keyapace

        CREATE KEYSPACE IF NOT EXISTS demo_cassadra_migration WITH REPLICATION =
            { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };
