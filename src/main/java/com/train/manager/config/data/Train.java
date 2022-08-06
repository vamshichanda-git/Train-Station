package com.train.manager.config.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * This class holds information about a train.
 *
 * @author vchanda
 */
public class Train {

    @JsonProperty
    private String trainCode;
    @JsonProperty
    private String trainName;
    @JsonProperty
    private List<String> route;

    public Train() {
    }

    public Train(String trainCode, String trainName, List<String> route) {
        this.trainCode = trainCode;
        this.trainName = trainName;
        this.route = route;
    }

    public void validate() {
        if (trainCode == null || trainName == null || route == null) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_113);
        }
    }

    public String getTrainCode() {
        return trainCode;
    }

    public String getTrainName() {
        return trainName;
    }

    public List<String> getRoute() {
        return route;
    }
}
