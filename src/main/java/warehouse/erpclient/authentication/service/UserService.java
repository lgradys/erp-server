package warehouse.erpclient.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.request_result.Error;
import warehouse.erpclient.request_result.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.RoleRepository;
import warehouse.erpclient.authentication.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NEW_USER_ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<RequestResult<UserDTO>> getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        RequestResult<UserDTO> requestResult;
        if (user.isPresent()) {
            UserDTO userDTO = UserDTO.of(user.get());
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(userDTO));
        } else {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error("Could not find user by username!")), List.of());
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<UserDTO>> addUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        RequestResult<UserDTO> requestResult;
        if (user.isPresent()) {
            requestResult = new RequestResult<>(HttpStatus.BAD_REQUEST.value(), List.of(new Error("Username is already used!")), List.of());
        } else {
            User newUser = User.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .role(getUserRole(NEW_USER_ROLE_NAME)).build();
            User savedUser = userRepository.save(newUser);
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(UserDTO.of(savedUser)));
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    private Role getUserRole(String roleName) {
        return roleRepository.findByRoleName(roleName).
                orElse(Role.builder().roleName(roleName).build());
    }

    @Transactional
    public ResponseEntity<RequestResult<UserDTO>> deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        RequestResult<UserDTO> requestResult;
        if (user.isEmpty()) {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error("Could not find user by username!")), List.of());
        } else {
            User loadedUser = user.get();
            userRepository.delete(loadedUser);
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(UserDTO.of(loadedUser)));
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

}
