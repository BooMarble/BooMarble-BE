package com.likelion.boomarble.domain.community.exception;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(String message){
        super(message);
    }
}
