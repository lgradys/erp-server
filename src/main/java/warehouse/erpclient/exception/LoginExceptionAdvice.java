package warehouse.erpclient.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import warehouse.erpclient.dto.UserDTO;

import static warehouse.erpclient.dto.UserDTO.createUnauthenticatedUserDTO;

@ControllerAdvice
public class LoginExceptionAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleException(RuntimeException exception, WebRequest webRequest) {
        return handleExceptionInternal(exception, UserDTO.builder().username(exception.getMessage()).build(), HttpHeaders.EMPTY, HttpStatus.UNAUTHORIZED, webRequest);
    }

//    @ResponseBody
//    @ExceptionHandler(LoginException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public UserDTO handleException(RuntimeException exception) {
//        return UserDTO.createUnauthenticatedUserDTO();
//    }

}
