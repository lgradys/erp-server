package warehouse.erpclient.authentication.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.NOT_FOUND);
    }

}
