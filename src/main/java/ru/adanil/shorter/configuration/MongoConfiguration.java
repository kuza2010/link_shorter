package ru.adanil.shorter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongo")
public class MongoConfiguration {

    private String mongoPath;
    private String configPath;

    public String getMongoPath() {
        return mongoPath;
    }

    public void setMongoPath(String mongoPath) {
        this.mongoPath = mongoPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
