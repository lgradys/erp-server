package warehouse.erpclient.authentication.service;

import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.authentication.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserDTO authenticate(LoginCredentials loginCredentials) {
        boolean requestBodyCheck = loginCredentials == null || loginCredentials.getUsername() == null || loginCredentials.getPassword() == null;
        if (requestBodyCheck) throw new AuthenticationException("Username and password can not be empty!");
        User user = userRepository.findByUsername(loginCredentials.getUsername()).orElseThrow(() -> new AuthenticationException("Incorrect username!"));
        if (!user.getPassword().equals(loginCredentials.getPassword())) throw new AuthenticationException("Incorrect password!");
        return UserDTO.of(user);
    }

}
