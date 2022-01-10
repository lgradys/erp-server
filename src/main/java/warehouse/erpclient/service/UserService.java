package warehouse.erpclient.service;

import warehouse.erpclient.exception.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.model.User;
import warehouse.erpclient.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserDTO authenticate(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) throw new LoginException("Username and password can not be empty!");
        User user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new LoginException("Incorrect username!"));
        if (!user.getPassword().equals(userDTO.getPassword())) throw new LoginException("Incorrect password!");
        return UserDTO.of(user);
    }

}
