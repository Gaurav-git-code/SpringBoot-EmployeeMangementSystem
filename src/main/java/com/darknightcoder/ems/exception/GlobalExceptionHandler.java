package com.darknightcoder.ems.exception;

import com.darknightcoder.ems.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException exception,
                                                                  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
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
             new Date(),
             exception.getMessage(),
             request.getDescription(false)
        );
       return new ResponseEntity<>(errorDetails,HttpStatus.FORBIDDEN);
    }
}
