package com.likelion.boomarble.global.error;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "유효하지 않은 값: " + ex.getValue() + ". " + ex.getName() + "은(는) 유효한 값을 제공해야 합니다.";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}