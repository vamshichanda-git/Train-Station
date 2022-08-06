package com.train.manager.config.builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.manager.config.Configuration;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * Builds the Configuration object from the provided parameters.
 *
 * @author vchanda
 */
public class ConfigurationBuilder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String configFilePath;

    public ConfigurationBuilder configFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
        return this;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public Configuration build() {
        Configuration config;
        try (InputStream fs = new FileInputStream(configFilePath)) {
            config = objectMapper.readValue(fs, Configuration.class);
        } catch (IOException e) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_102, e);
        } catch (Exception e) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_103, e);
        }

        config.initAndValidate();
        return config;
    }
}
