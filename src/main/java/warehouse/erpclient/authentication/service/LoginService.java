package warehouse.erpclient.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.request_result.Error;
import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.request_result.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public ResponseEntity<RequestResult<UserDTO>> authenticate(LoginCredentials loginCredentials, HttpServletResponse response) {
        Optional<User> user = userRepository.findByUsername(loginCredentials.getUsername());
        RequestResult<UserDTO> requestResult;
        if (user.isPresent()) {
            User loadedUser = user.get();
            if (loadedUser.getPassword().equals(loginCredentials.getPassword())) {
                UserDTO loadedUserDTO = UserDTO.of(loadedUser);
                requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(loadedUserDTO));
                jwtService.addTokenToResponse(loadedUserDTO, response);
            } else {
                requestResult = new RequestResult<>(HttpStatus.UNAUTHORIZED.value(), List.of(new Error("Incorrect password!")), List.of());
            }
        } else {
            requestResult = new RequestResult<>(HttpStatus.UNAUTHORIZED.value(), List.of(new Error("Incorrect username!")), List.of());
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

}
