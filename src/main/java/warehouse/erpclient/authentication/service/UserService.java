package warehouse.erpclient.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;
import warehouse.erpclient.utils.dto.RequestResult;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<RequestResult<UserDTO>> getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserDTO userDTO = user.map(UserDTO::of).orElse(null);
        return RequestResult.createResponse(userDTO);
    }

    public ResponseEntity<RequestResult<UserDTO>> addUser(UserDTO userDTO) {
        User savedUser = userRepository.save(User.createNewUser(userDTO));
        return RequestResult.createResponse(UserDTO.of(savedUser));
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
