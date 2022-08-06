package com.train.manager.config.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * This class holds information about a path between 2 train stations.
 *
 * @author vchanda
 */
public class Path {

    @JsonProperty
    private String sourceStation;
    @JsonProperty
    private String destinationStation;
    @JsonProperty
    private Double distance;

    public Path() {
    }

    public Path(String sourceStation, String destinationStation, Double distance) {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.distance = distance;
    }

    public void validate() {
        if (sourceStation == null || destinationStation == null || distance == null) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_111);
        }
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public Double getDistance() {
        return distance;
    }
}
