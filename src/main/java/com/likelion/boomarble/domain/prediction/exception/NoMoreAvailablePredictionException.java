package com.likelion.boomarble.domain.prediction.exception;

public class NoMoreAvailablePredictionException extends RuntimeException{
    public NoMoreAvailablePredictionException(String message){
        super(message);
    }
}