package com.crud.cadastrousuario.api.exceptionhandler;

import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.ExceptionDetails;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.exception.ValidationExceptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;


   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(
           MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
       List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

       String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
       String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

       return new ResponseEntity<>(
               ValidationExceptionDetails.builder()
                       .timestamp(LocalDateTime.now(ZoneId.systemDefault()))
                       .message("Check the field(s) error")
                       .status(HttpStatus.BAD_REQUEST.value())
                       .fields(fields)
                       .fieldsMessage(fieldsMessage)
                       .build(), HttpStatus.BAD_REQUEST);
   }


    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionDetails> pessoaNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now(ZoneId.systemDefault()))
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionDetails> handleBadRequestException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now(ZoneId.systemDefault()))
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build(), HttpStatus.BAD_REQUEST);
    }


}
