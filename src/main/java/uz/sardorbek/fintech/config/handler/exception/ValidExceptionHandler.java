package uz.sardorbek.fintech.config.handler.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.sardorbek.fintech.config.handler.exception.customException.ElementNotFoundException;
import uz.sardorbek.fintech.config.utils.global_response.ResponseObject;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ValidExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        String message = "Path variable [" + ex.getVariableName() + "] is required";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                message,
                ex.getMessage()
        ));
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        String message = "This request doesn't wait [" + ex.getMethod() + "] HttpMethod";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ResponseObject(
                message,
                ex.getMessage()
        ));
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        String message = "Request [" + ex.getParameterName() + "] is required";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                message,
                ex.getMessage()
        ));
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        List<String> errorFields = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorFields.add("Validation message: " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Validation Error", errorFields));
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ex.getMessage().contains(" body is missing")
                        ? new ResponseObject("Error: Body is missing", "Request Body is Required")
                        : new ResponseObject("Error: Message Not Readable", ex.getMessage())
        );
    }


    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(
                        "Error: Not Supported Media Type",
                        "Http media type [" + Objects.requireNonNull(ex.getContentType()).getType() + "] not supported"
                )
        );
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(
                        "Error: Missing Request Part",
                        "Request part: [" + ex.getRequestPartName() + "] missing"
                )
        );
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NotNull NoHandlerFoundException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found exception",
                ex.getMessage()
        ));
    }


    @NotNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, @NotNull HttpHeaders headers, @NotNull HttpStatusCode statusCode, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Some internal Exception",
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Error: Entity Not Found ",
                        "Element in table not found : " + e.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlePostgresqlException(SQLException e) {
        String exception;
        if (e.getMessage().contains("foreign key")) {
            exception = "Element has relation";
        } else if (e.getMessage().contains("duplicate")) {
            exception = "Duplicate Key Value";
        } else {
            exception = "Postgresql Exception";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(exception, e.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        "No Such Element",
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNullPointException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Null Point Exception",
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleOfFileSizeLimit(FileSizeLimitExceededException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "File size is big then 15 Mb",
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> notFoundUsername(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseObject("Username not found exception",
                        e.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleElementNotFoundException(ElementNotFoundException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Element not found: " + e.getMessage(),
                        "Element in table not found"
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ResponseObject(
                                "Access Denied!",
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ResponseObject(
                                "Parameter is invalid",
                                e.getMessage()
                        )
                );
    }
}
