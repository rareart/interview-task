package ru.sberbank.interview.task.controller.handler;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sberbank.interview.task.controller.dto.support.ErrorResponse;
import ru.sberbank.interview.task.exception.MissingIdException;

import java.text.ParseException;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(MissingIdException.class)
    public ResponseEntity<ErrorResponse> handleException(MissingIdException e){
        ErrorResponse errorResponse = new ErrorResponse(
                404, e.getMessage(), e.getMissingIds().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Unable to read your request",
                        e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(ParseException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "internal preload parsing error",
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(TransactionException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "internal server database error",
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(BeanCreationException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "Internal server fatal init error",
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "Server returned an unexpected error: " + e.getClass().getName(),
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
