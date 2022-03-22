package warehouse.erpclient.authentication.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import warehouse.erpclient.authentication.component.PasswordEncoder;
import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static UserDTO userDTO;
    private static User user;

    private HttpServletResponse response;

    @BeforeAll
    static void beforeAll() {
        userDTO = UserDTO.builder()
                .username("user")
                .password("password")
                .role(Role.USER)
                .build();
        user = User.builder()
                .id(1)
                .username("user")
                .password("password".getBytes(StandardCharsets.UTF_8))
                .role(Role.USER)
                .build();
    }

    @BeforeEach
    void setUp() {
        this.response = new MockHttpServletResponse();
    }

    @Test
    void shouldReturnUserWhenCredentialsAreCorrect() {
        //given
        LoginCredentials loginCredentials = new LoginCredentials(user.getUsername(), new String(user.getPassword(), StandardCharsets.UTF_8));
        given(userRepository.findByUsername(loginCredentials.getUsername())).willReturn(Optional.of(user));
        given(passwordEncoder.encode(any(byte[].class))).willReturn(loginCredentials.getPassword().getBytes(StandardCharsets.UTF_8));

        //when
        RequestResult<UserDTO> requestResult = loginService.authenticate(loginCredentials, response).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getResource()).size().isEqualTo(1);
        verify(jwtService).addTokenToResponse(userDTO, response);
    }

    @Test
    void shouldReturnErrorWhenUsernameIsIncorrect() {
        //given
        String username = "incorrectUsername";
        LoginCredentials loginCredentials = new LoginCredentials(username, new String(user.getPassword(), StandardCharsets.UTF_8));
        given(userRepository.findByUsername(loginCredentials.getUsername())).willReturn(Optional.empty());

        //when
        RequestResult<UserDTO> requestResult = loginService.authenticate(loginCredentials, response).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getError()).size().isEqualTo(1);
        verify(jwtService, never()).addTokenToResponse(userDTO, response);
    }

    @Test
    void shouldReturnErrorWhenPasswordIsIncorrect() {
        //given
        String password = "incorrectPassword";
        LoginCredentials loginCredentials = new LoginCredentials(user.getUsername(), password);
        given(userRepository.findByUsername(loginCredentials.getUsername())).willReturn(Optional.of(user));
        given(passwordEncoder.encode(any(byte[].class))).willReturn(loginCredentials.getPassword().getBytes(StandardCharsets.UTF_8));

        //when
        RequestResult<UserDTO> requestResult = loginService.authenticate(loginCredentials, response).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getError()).size().isEqualTo(1);
        verify(jwtService, never()).addTokenToResponse(userDTO, response);
    }

}