package com.rayllanderson.gerenciadordecompras.api.exceptions.handle;

import com.rayllanderson.gerenciadordecompras.domain.exceptions.BadGatewayException;
import com.rayllanderson.gerenciadordecompras.domain.exceptions.BadRequestException;
import com.rayllanderson.gerenciadordecompras.domain.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Not Found")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<StandardError> handleMaxUploadSizeExceededException(HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request")
                        .message("Tamanho máximo de upload excedido. Tamanho máximo: 200KB")
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<StandardError> handleBadGatewayException(BadGatewayException e, HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.BAD_GATEWAY;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Gateway")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException e, @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return ResponseEntity.status(status).body(
                ValidationError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request. Invalid Fields")
                        .message(e.getMessage())
                        .path(request.getContextPath())
                        .status(status.value())
                        .fields(fields)
                        .fieldsMessage(fieldsMessages)
                        .build());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, @NonNull HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(ex.getCause().getMessage())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .status(status.value())
                .build();
        return new ResponseEntity<>(standardError, headers, status);
    }
}
