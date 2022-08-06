package com.train.manager;


import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.train.manager.config.Configuration;
import com.train.manager.config.builder.ConfigurationBuilder;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;
import com.train.manager.runtime.TrainJourney;
import com.train.manager.runtime.builder.TrainJourneysBuilder;
import com.train.manager.runtime.TrainJourneyMerger;


public class TrainManager {

    private static final String CONFIG_FILE_NAME = "application_config.json";
    private static final String applicationResourcesPath = Paths.get("src", "main", "resources").toAbsolutePath() + File.separator;

    public static void main(String[] args) {
        // validates the user provided command line arguments
        if (args == null || args.length <= 0) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_101);
        }

        // the config file can also be a user input, if not provided any it will go to default location of the resources folder
        String configFilePath = applicationResourcesPath + CONFIG_FILE_NAME;
        if (args.length > 1) {
            configFilePath = args[1];
        }
        // initialize the configuration from the config json file
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        Configuration configuration = configurationBuilder.configFilePath(configFilePath).build();

        // read the train journey information from the input file
        TrainJourneysBuilder trainJourneysBuilder = new TrainJourneysBuilder();
        List<TrainJourney> trainJourneys = trainJourneysBuilder.trainJourneysFilePath(args[0]).configuration(configuration).build();

        // make each train travel until the merger station provided in the config
        trainJourneys.forEach(TrainJourney::travelUntilMergerStation);
        // print the bogie arrival order of the trains
        trainJourneys.forEach(TrainJourney::printArrivedBogies);
        // depart all the bogies of the merger station before merging
        trainJourneys.forEach(TrainJourney::leaveMergerStation);

        // merge the bogies of the trains arrived at the merger station
        TrainJourneyMerger trainJourneyMerger = new TrainJourneyMerger();
        TrainJourney mergedTrainJourney = trainJourneyMerger.mergeTrainJourneys(trainJourneys, configuration);
        // print the bogie order while departure
        mergedTrainJourney.printDepartedBogies();
    }
}
