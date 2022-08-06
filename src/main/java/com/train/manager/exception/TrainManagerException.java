package com.train.manager.exception;

public class TrainManagerException extends RuntimeException {

    private final TrainManagerExceptionCode trainManagerExceptionCode;
    private final String[] exceptionMessageFormatArgs;

    public TrainManagerException(TrainManagerExceptionCode trainManagerExceptionCode, String... exceptionMessageFormatArgs) {
        this.trainManagerExceptionCode = trainManagerExceptionCode;
        this.exceptionMessageFormatArgs = exceptionMessageFormatArgs;
    }

    public TrainManagerException(TrainManagerExceptionCode trainManagerExceptionCode, Throwable throwable, String... exceptionMessageFormatArgs) {
        super(throwable);
        this.trainManagerExceptionCode = trainManagerExceptionCode;
        this.exceptionMessageFormatArgs = exceptionMessageFormatArgs;
    }

    public TrainManagerExceptionCode getTrainManagerExceptionCode() {
        return trainManagerExceptionCode;
    }

    @Override
    public String toString() {
        return String.format(trainManagerExceptionCode.toString(), exceptionMessageFormatArgs) + "\n" + super.toString();
    }
}
