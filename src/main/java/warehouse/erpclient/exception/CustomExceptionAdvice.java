package warehouse.erpclient.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.erpclient.request_result.Error;
import warehouse.erpclient.request_result.RequestResult;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestResult<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<Error> errorList = createErrorList(exception);
        RequestResult<Object> requestResult = new RequestResult<>(HttpStatus.BAD_REQUEST.value(), errorList, List.of());
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<RequestResult<?>> handleSQLException(SQLException exception) {
        List<Error> errors = List.of(new Error("Unprocessable entity!"));
        RequestResult<Object> requestResult = new RequestResult<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), errors, List.of());
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    private List<Error> createErrorList(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .map(Error::new)
                .collect(Collectors.toList());
    }

}
