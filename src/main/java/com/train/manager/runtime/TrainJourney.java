package com.train.manager.runtime;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.train.manager.config.Configuration;
import com.train.manager.config.data.Train;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * This class holds information about a train journey.
 *
 * @author vchanda
 */
public class TrainJourney {

    private final String trainCode;
    private final List<String> journey;
    private final Configuration configuration;

    public TrainJourney(List<String> journey, Configuration configuration) {
        this.trainCode = journey.get(0);
        this.journey = new LinkedList<>(journey);
        this.configuration = configuration;
    }

    public void validate() {
        // validate if the train object is found
        findTrain();
    }

    /**
     * Travels the train until the merger station.
     */
    public void travelUntilMergerStation() {
        travelUntilStation(configuration.getMergerStation());
    }

    /**
     * Travels the train until the provided station.
     */
    public void travelUntilStation(String station) {
        List<String> route = findTrainRoute();

        Set<String> stationsUntilMerger = new HashSet<>(route.subList(0, route.indexOf(station)));
        journey.removeIf(stationsUntilMerger::contains);
    }

    /**
     * Depart all the bogies until the merger station.
     */
    public void leaveMergerStation() {
        leaveStation(configuration.getMergerStation());
    }

    /**
     * Depart all the bogies until the provided station.
     */
    public void leaveStation(String station) {
        journey.removeIf(bogie -> bogie.equals(station));
    }

    /**
     * Display the order of the bogies arrived at the merger station.
     */
    public void printArrivedBogies() {
        System.out.println(configuration.getMergerStationArrivalOpStr() + " " + String.join(" ", journey));
    }

    /**
     * Display the order of the bogies departing from the merger station.
     */
    public void printDepartedBogies() {
        // if no passenger bogies are left, display that the journey has ended
        if(noBogiesLeft()){
            System.out.println(configuration.getJourneyEndedMessage());
            return;
        }
        System.out.println(configuration.getMergerStationDepartureOpStr() + " " + String.join(" ", journey));
    }

    /**
     * Checks if the current train has any passenger bogies left.
     */
    private boolean noBogiesLeft() {
        return journey.isEmpty() || journey.get(journey.size() - 1).equalsIgnoreCase(configuration.getEngineBogieCode());
    }

    /**
     * Finds and returns the route of the train travel.
     */
    private List<String> findTrainRoute() {
        return findTrain().getRoute();
    }

    /**
     * Finds and returns the defined train.
     */
    private Train findTrain() {
        Optional<Train> trainRoute = configuration.getTrains().stream().filter(train -> train.getTrainCode().equalsIgnoreCase(trainCode)).findFirst();
        if (!trainRoute.isPresent()) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_106,
                    trainCode, configuration.getTrains().stream().map(Train::getTrainCode).collect(Collectors.joining(", ")));
        }
        return trainRoute.get();
    }

    public List<String> getJourney() {
        return journey;
    }

    public String getTrainCode() {
        return trainCode;
    }
}
