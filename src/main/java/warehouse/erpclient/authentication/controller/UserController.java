package warehouse.erpclient.authentication.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Hidden
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<RequestResult<UserDTO>> getUser(@PathVariable String username) {
        return userService.getUser(username);
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
