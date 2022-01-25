package warehouse.erpclient.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.authentication.dto.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.service.LoginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<RequestResult<UserDTO>> login(@RequestBody @Valid LoginCredentials loginCredentials, HttpServletResponse response) {
        return loginService.authenticate(loginCredentials, response);
    }

}
