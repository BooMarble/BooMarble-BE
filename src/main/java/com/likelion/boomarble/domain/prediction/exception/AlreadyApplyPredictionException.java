package com.likelion.boomarble.domain.prediction.exception;

public class AlreadyApplyPredictionException extends RuntimeException{
    public AlreadyApplyPredictionException(String message){
        super(message);
    }
}