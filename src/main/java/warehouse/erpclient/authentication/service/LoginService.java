package warehouse.erpclient.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.authentication.component.PasswordEncoder;
import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;
import warehouse.erpclient.utils.dto.RequestResult;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<RequestResult<UserDTO>> authenticate(LoginCredentials loginCredentials, HttpServletResponse response) {
        Optional<User> user = userRepository.findByUsername(loginCredentials.getUsername());
        if (user.isPresent()) {
            User loadedUser = user.get();
            if (encodeRequestPassword(loginCredentials).equals(new String(loadedUser.getPassword(), StandardCharsets.UTF_8))) {
                UserDTO loadedUserDTO = UserDTO.of(loadedUser);
                jwtService.addTokenToResponse(loadedUserDTO, response);
                return RequestResult.createResponse(loadedUserDTO);
            } else {
                return RequestResult.createResponse(null, HttpStatus.UNAUTHORIZED, "Incorrect password!");
            }
        } else {
            return RequestResult.createResponse(null, HttpStatus.UNAUTHORIZED, "Incorrect username!");
        }
    }

    private String encodeRequestPassword(LoginCredentials loginCredentials) {
        byte[] encodeArray = passwordEncoder.encode(loginCredentials.getPassword().getBytes(StandardCharsets.UTF_8));
        return new String(encodeArray, StandardCharsets.UTF_8);
    }

}
