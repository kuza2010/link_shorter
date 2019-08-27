package ru.adanil.shorter.configuration;

import com.mongodb.lang.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "mongo")
@Validated
public class MongoConfiguration {

    @NonNull
    private String mongoPath;
    @NonNull
    private String configPath;
    @NonNull
    private String scriptPath;

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

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }
}
