package warehouse.erpclient.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.erpclient.authentication.dto.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<RequestResult<UserDTO>> getUser(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/user")
    public ResponseEntity<RequestResult<UserDTO>> addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<RequestResult<UserDTO>> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

}
