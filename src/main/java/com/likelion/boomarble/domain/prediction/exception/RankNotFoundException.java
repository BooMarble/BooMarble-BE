package com.likelion.boomarble.domain.prediction.exception;

public class RankNotFoundException extends RuntimeException{
    public RankNotFoundException(String message){
        super(message);
    }
}