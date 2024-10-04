package me.vitorcsouza.star_wars_api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> Error(Exception exception) {
        ResponseError responseError = new ResponseError(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> noSuchElementException( NoSuchElementException exception){
        ResponseError responseError = new ResponseError(
                "Not found.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(usuarioDuplicadoException.class)
    public ResponseEntity<ResponseError> usuarioDuplicadoException( usuarioDuplicadoException exception){
        ResponseError responseError = new ResponseError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
}
