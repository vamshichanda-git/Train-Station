package com.train.manager.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.train.manager.config.Configuration;
import com.train.manager.config.data.Path;

/**
 * This class facilitates the merging of the bogies of the provided trains.
 */
public class TrainJourneyMerger {

    /**
     * This method merges the bogies in the given list of trains from the merger station provided in the configuration
     * The merged train has bogies in the decreasing order of distances from the merger station to the bogie destination station
     */
    public TrainJourney mergeTrainJourneys(List<TrainJourney> trainJourneys, Configuration configuration) {
        List<String> mergedTrainJourneyHead = buildMergedTrainJourneyHead(trainJourneys, configuration);
        TrainJourney mergedTrainJourney = new TrainJourney(mergedTrainJourneyHead, configuration);

        Map<String, Double> distanceFromMergerStation = getDistancesFromMergerStation(configuration);

        List<String> mergedBogies = new ArrayList<>();
        for (TrainJourney trainJourney : trainJourneys) {
            mergedBogies(mergedBogies, trainJourney, distanceFromMergerStation);
        }

        mergedTrainJourney.getJourney().addAll(mergedBogies);
        return mergedTrainJourney;
    }

    /**
     * This method returns a map of station code and distances of the station from the merger station
     */
    private Map<String, Double> getDistancesFromMergerStation(Configuration configuration) {
        Map<String, Double> distanceFromMergerStation = new HashMap<>();
        calculateDistance(configuration.getMergerStation(), (double) 0, configuration, distanceFromMergerStation);
        return distanceFromMergerStation;
    }

    /**
     * This method is used to calculate the total distance from a source station i.e, the merger station and the destination station
     */
    private void calculateDistance(String sourceStation, Double travelledDistance, Configuration config, Map<String, Double> distances) {
        distances.put(sourceStation, travelledDistance);
        Set<Path> pathsFromSource = config.getSourceToDestination().get(sourceStation);

        if (pathsFromSource == null) {
            return;
        }

        for (Path path : pathsFromSource) {
            calculateDistance(path.getDestinationStation(), travelledDistance + path.getDistance(), config, distances);
        }
    }

    /**
     * This method creates the train journey with the New Merged train name and the all the engines from the provided trains list
     */
    private List<String> buildMergedTrainJourneyHead(List<TrainJourney> trainJourneys, Configuration configuration) {
        List<String> mergedTrainJourneyHead = new ArrayList<>();
        mergedTrainJourneyHead.add(getMergedTrainName(trainJourneys, configuration));
        for (int trainIndex = 0; trainIndex < trainJourneys.size(); trainIndex++) {
            mergedTrainJourneyHead.add(configuration.getEngineBogieCode());
        }
        return mergedTrainJourneyHead;
    }

    /**
     * Train codes are in the format TRAIN_X.
     * Merged train name has the format TRAIN_XY
     */
    private String getMergedTrainName(List<TrainJourney> trainJourneys, Configuration configuration) {
        StringBuilder mergedTrainName = new StringBuilder(configuration.getTrainCodePrefix());
        trainJourneys.forEach(trainJourney -> mergedTrainName.append(trainJourney.getTrainCode().substring(configuration.getTrainCodePrefix().length())));
        return mergedTrainName.toString();
    }

    /**
     * This method merges 2 train bogies and orders them in the decreasing order of distances from the merger station to the bogie destination station
     */
    private void mergedBogies(List<String> mergedBogies, TrainJourney journeyToMerge, Map<String, Double> distanceFromMergerStation) {
        mergedBogies.addAll(journeyToMerge.getJourney().subList(2, journeyToMerge.getJourney().size()));

        mergedBogies.sort((bogie1, bogie2) -> {
            if (distanceFromMergerStation.get(bogie2).equals(distanceFromMergerStation.get(bogie1))) {
                return 0;
            } else if (distanceFromMergerStation.get(bogie2) < distanceFromMergerStation.get(bogie1)) {
                return -1;
            } else {
                return 1;
            }
        });
    }
}
