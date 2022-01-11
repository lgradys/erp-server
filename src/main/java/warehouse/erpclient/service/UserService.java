package warehouse.erpclient.service;

import warehouse.erpclient.dto.LoginCredentials;
import warehouse.erpclient.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.model.User;
import warehouse.erpclient.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserDTO authenticate(LoginCredentials loginCredentials) {
        if (loginCredentials.getUsername() == null || loginCredentials.getPassword() == null) throw new AuthenticationException("Username and password can not be empty!");
        User user = userRepository.findByUsername(loginCredentials.getUsername()).orElseThrow(() -> new AuthenticationException("Incorrect username!"));
        if (!user.getPassword().equals(loginCredentials.getPassword())) throw new AuthenticationException("Incorrect password!");
        return UserDTO.of(user);
    }

}
