package warehouse.erpclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import warehouse.erpclient.dto.LoginCredentials;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.service.JWTService;
import warehouse.erpclient.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginCredentials loginCredentials, HttpServletResponse response) {
        UserDTO user = userService.authenticate(loginCredentials);
        jwtService.addTokenToResponse(user, response);
        return user;
    }

}
