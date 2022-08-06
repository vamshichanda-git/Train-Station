package com.train.manager.config.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

/**
 * This class holds information about a train station.
 *
 * @author vchanda
 */
public class Station {

    @JsonProperty
    private String stationCode;
    @JsonProperty
    private String stationName;

    public Station() {
    }

    public Station(String stationCode, String stationName) {
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    public void validate() {
        if (stationCode == null || stationName == null) {
            throw new TrainManagerException(TrainManagerExceptionCode.TME_112);
        }
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }
}
