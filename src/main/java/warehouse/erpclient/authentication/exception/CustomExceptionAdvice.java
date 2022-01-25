package warehouse.erpclient.authentication.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.erpclient.authentication.dto.Error;
import warehouse.erpclient.authentication.dto.RequestResult;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestResult<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<Error> errorList = createErrorList(exception);
        RequestResult<Object> requestResult = new RequestResult<>(HttpStatus.BAD_REQUEST.value(), errorList, List.of());
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    private List<Error> createErrorList(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .map(Error::new)
                .collect(Collectors.toList());
    }

}
