package com.train.manager.runtime.builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.manager.config.Configuration;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;
import com.train.manager.runtime.TrainJourney;

/**
 * Builds the list of train journey objects from the provided parameters.
 *
 * @author vchanda
 */
public class TrainJourneysBuilder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String trainJourneysFilePath;
    private Configuration configuration;

    public TrainJourneysBuilder trainJourneysFilePath(String trainJourneysFilePath) {
        this.trainJourneysFilePath = trainJourneysFilePath;
        return this;
    }

    public TrainJourneysBuilder configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public void setTrainJourneysFilePath(String trainJourneysFilePath) {
        this.trainJourneysFilePath = trainJourneysFilePath;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<TrainJourney> build() {
        List<TrainJourney> trainJourneys;
        try (Scanner sc = new Scanner(new FileInputStream(trainJourneysFilePath))) {
            trainJourneys = new ArrayList<>();
            while (sc.hasNextLine()) {
                TrainJourney trainJourney = new TrainJourney(Arrays.asList(sc.nextLine().split(" ")), configuration);
                trainJourneys.add(trainJourney);
            }
        } catch (IOException e) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_104, e);
        } catch (Exception e) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_105, e);
        }
        trainJourneys.forEach(TrainJourney::validate);
        return trainJourneys;
    }
}
