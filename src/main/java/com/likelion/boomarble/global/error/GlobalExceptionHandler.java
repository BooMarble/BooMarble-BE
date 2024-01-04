package com.likelion.boomarble.global.error;

import com.likelion.boomarble.domain.prediction.exception.AlreadyApplyPredictionException;
import com.likelion.boomarble.domain.prediction.exception.InvalidScoreException;
import com.likelion.boomarble.domain.prediction.exception.NoMoreAvailablePredictionException;
import com.likelion.boomarble.domain.prediction.exception.NotAvailableRecommendationLetterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "유효하지 않은 값: " + ex.getValue() + ". " + ex.getName() + "은(는) 유효한 값을 제공해야 합니다.";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidScoreException.class)
    public ResponseEntity<Object> handleInvalidJapaneseScoreException(InvalidScoreException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyApplyPredictionException.class)
    public ResponseEntity<Object> handleAlreadyApplyPredictionException(AlreadyApplyPredictionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoMoreAvailablePredictionException.class)
    public ResponseEntity<Object> handelNoMoreAvailablePredictionException(NoMoreAvailablePredictionException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAvailableRecommendationLetterException.class)
    public ResponseEntity<Object> handleNotAvailableRecommendationLetterException(NotAvailableRecommendationLetterException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}