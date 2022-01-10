package warehouse.erpclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.service.UserService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO userDTO) {
        return userService.authenticate(userDTO);
    }

}
