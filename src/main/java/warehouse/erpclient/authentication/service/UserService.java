package warehouse.erpclient.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.authentication.component.PasswordEncoder;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;
import warehouse.erpclient.utils.dto.RequestResult;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<RequestResult<UserDTO>> getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserDTO userDTO = user.map(UserDTO::of).orElse(null);
        return RequestResult.createResponse(userDTO);
    }

    public ResponseEntity<RequestResult<UserDTO>> addUser(UserDTO userDTO) {
        User savedUser = userRepository.save(createNewUser(userDTO));
        return RequestResult.createResponse(UserDTO.of(savedUser));
    }

    private User createNewUser(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword().getBytes(StandardCharsets.UTF_8)))
                .role(Role.USER)
                .enabled(true)
                .build();
    }

    @Transactional
    public ResponseEntity<RequestResult<UserDTO>> deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserDTO userDTO = user.map(value -> {
            userRepository.delete(value);
            return UserDTO.of(value);
        }).orElse(null);
        return RequestResult.createResponse(userDTO);
    }

}
