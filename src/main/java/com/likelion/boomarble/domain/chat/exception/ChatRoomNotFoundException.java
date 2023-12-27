package com.likelion.boomarble.domain.chat.exception;

public class ChatRoomNotFoundException extends RuntimeException{
    public ChatRoomNotFoundException(String message){super(message);}
}