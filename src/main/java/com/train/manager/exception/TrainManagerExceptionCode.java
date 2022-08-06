package com.train.manager.exception;

public enum TrainManagerExceptionCode {
    TME_101("TME_101", "Please provide valid arguments for train journeys. Expected file paths for train journeys."),
    TME_102("TME_102", "Unable to read the config file."),
    TME_103("TME_103", "Unable to initialize configuration."),
    TME_104("TME_104", "Unable to read the input file."),
    TME_105("TME_105", "Unable to process the train journeys input."),
    TME_106("TME_106", "Please provide a valid Train Code for the journey. Provided: {}, Expected: [{}])"),
    TME_107("TME_107", "Please provide a valid train code and distance for the path. Provided Source: {}, Destination: {}, Distance: {}"),
    TME_108("TME_108", "Please provide a valid train code for merger station"),
    TME_109("TME_109", "Please provide a valid path for the train. Provided invalid station code: {}"),
    TME_110("TME_110", "Please provide a valid values for configuration"),
    TME_111("TME_111", "Please provide valid values for Path between stations"),
    TME_112("TME_112", "Please provide valid values for Station"),
    TME_113("TME_113", "Please provide valid values for Train");

    private final String exceptionCode;
    private final String exceptionMessageFormat;

    TrainManagerExceptionCode(String exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessageFormat = exceptionMessage;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public String getExceptionMessageFormat() {
        return exceptionMessageFormat;
    }

    @Override
    public String toString() {
        return getExceptionCode() + ":" + getExceptionMessageFormat();
    }
}
