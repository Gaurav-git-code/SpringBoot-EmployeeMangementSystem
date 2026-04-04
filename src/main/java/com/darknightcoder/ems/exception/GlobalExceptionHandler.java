package com.darknightcoder.ems.exception;

import com.darknightcoder.ems.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException exception,
                                                                  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                Instant.now(),
                exception.getMessage(),
                request.getDescription(false)

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteResourceException.class)
    public ResponseEntity<ErrorDetails> deleteResourceException(
            DeleteResourceException exception,
            WebRequest request
    ){
       ErrorDetails errorDetails = new ErrorDetails(
               Instant.now(),
               exception.getMessage(),
               request.getDescription(false)
        );
       return new ResponseEntity<>(errorDetails,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidException(
       MethodArgumentNotValidException exception,
       WebRequest request
    ){
        String errorMessage = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(field -> field.getField() + " : " + field.getDefaultMessage())
                .collect(Collectors.joining(","));
        ErrorDetails errorDetails = new ErrorDetails(
                Instant.now(),
                errorMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
}
