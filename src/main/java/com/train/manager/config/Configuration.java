package com.train.manager.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.train.manager.config.data.Path;
import com.train.manager.config.data.Station;
import com.train.manager.config.data.Train;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * This class holds the information required for the train journey such as the stations, trains, routes.
 * This is created from the config file.
 *
 * @author vchanda
 */
public class Configuration {

    @JsonProperty
    private List<Station> stations;
    @JsonProperty
    private List<Path> paths;
    @JsonProperty
    private List<Train> trains;
    @JsonProperty
    private String mergerStation;
    @JsonProperty
    private String engineBogieCode;
    @JsonProperty
    private String trainCodePrefix;
    @JsonProperty
    private String mergerStationArrivalOpStr;
    @JsonProperty
    private String mergerStationDepartureOpStr;
    @JsonProperty
    private String journeyEndedMessage;

    private Set<String> stationCodes;
    private Map<String, Set<Path>> sourceToDestination;

    public Configuration() {
    }

    public Configuration(List<Station> stations, List<Path> paths, List<Train> trains, String mergerStation, String engineBogieCode, String trainCodePrefix, String mergerStationArrivalOpStr, String departureStationArrivalOpStr, String journeyEndedMessage) {
        this.stations = stations;
        this.paths = paths;
        this.trains = trains;
        this.mergerStation = mergerStation;
        this.engineBogieCode = engineBogieCode;
        this.trainCodePrefix = trainCodePrefix;
        this.mergerStationArrivalOpStr = mergerStationArrivalOpStr;
        this.mergerStationDepartureOpStr = departureStationArrivalOpStr;
        this.journeyEndedMessage = journeyEndedMessage;
    }

    public void initAndValidate() {
        validateBeforeInit();
        init();
        validate();
    }

    private void validateBeforeInit() {
        if (mergerStation == null || stations == null || paths == null || trains == null || engineBogieCode == null || trainCodePrefix == null) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_110);
        }
    }

    private void validate() {
        // validate trains have valid values
        trains.forEach(Train::validate);

        // validate stations have valid values
        stations.forEach(Station::validate);

        // validate paths have valid values
        paths.forEach(Path::validate);

        // validate the paths only have the defined stations
        Optional<Path> invalidPath = paths.stream().filter(path ->
                (!stationCodes.contains(path.getSourceStation())
                        || !stationCodes.contains(path.getDestinationStation())
                        || path.getDistance() < 0)).findAny();

        if (invalidPath.isPresent()) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_107, invalidPath.get().getSourceStation(), invalidPath.get().getDestinationStation(), invalidPath.get().getDistance().toString());
        }

        // validate the train route
        Optional<String> invalidStationInPath = trains.stream().map(Train::getRoute).flatMap(List::stream).filter(stationCode -> !stationCodes.contains(stationCode)).findAny();
        if (invalidStationInPath.isPresent()) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_109, invalidStationInPath.get());
        }

        // validate merger station
        if (!stationCodes.contains(mergerStation)) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_108);
        }
    }

    /**
     * Initializes fields of the configuration built upon information provided in the config file.
     */
    private void init() {
        stationCodes = stations.stream().map(Station::getStationCode).collect(Collectors.toSet());

        sourceToDestination = new HashMap<>();
        paths.stream().forEach(path -> {
            sourceToDestination.putIfAbsent(path.getSourceStation(), new HashSet<>());
            sourceToDestination.get(path.getSourceStation()).add(path);
        });
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public String getMergerStation() {
        return mergerStation;
    }

    public String getEngineBogieCode() {
        return engineBogieCode;
    }

    public String getTrainCodePrefix() {
        return trainCodePrefix;
    }

    public String getMergerStationArrivalOpStr() {
        return mergerStationArrivalOpStr;
    }

    public String getMergerStationDepartureOpStr() {
        return mergerStationDepartureOpStr;
    }

    public String getJourneyEndedMessage() {
        return journeyEndedMessage;
    }

    public Map<String, Set<Path>> getSourceToDestination() {
        return sourceToDestination;
    }
}
