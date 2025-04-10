package com.crud.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class MongoDBTestConfig {

    @Bean
    public MongoDBContainer mongoDBContainer() {
        MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.8"));
        mongoDBContainer.start();
        return mongoDBContainer;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDBContainer mongoDBContainer) {
        String connectionString = mongoDBContainer.getReplicaSetUrl();
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(connectionString));
    }
}
