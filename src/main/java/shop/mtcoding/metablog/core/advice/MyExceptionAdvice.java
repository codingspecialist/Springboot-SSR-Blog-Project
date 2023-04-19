package shop.mtcoding.metablog.core.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import shop.mtcoding.metablog.core.exception.csr.*;
import shop.mtcoding.metablog.core.exception.ssr.*;
import shop.mtcoding.metablog.dto.ResponseDTO;

@Slf4j
@ControllerAdvice
public class MyExceptionAdvice {

    ////////////////////////////// VIEW
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.NOT_FOUND, "notFound", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ////////////////////////////// API
    @ExceptionHandler(ExceptionApi400.class)
    public ResponseEntity<?> badRequest(ExceptionApi400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> unAuthorized(ExceptionApi401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> forbidden(ExceptionApi403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> notFound(ExceptionApi404 e){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.NOT_FOUND, "notFound", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExceptionApi500.class)
    public ResponseEntity<?> serverError(ExceptionApi500 e){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //////////////////////////// COMMON
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
