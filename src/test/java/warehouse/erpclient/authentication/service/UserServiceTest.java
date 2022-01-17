package warehouse.erpclient.authentication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import warehouse.erpclient.authentication.dto.LoginCredentials;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.exception.AuthenticationException;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        response = new MockHttpServletResponse();
    }

    @Test
    void shouldThrowExceptionWhenCredentialsAreNull() {
        assertThrows(AuthenticationException.class, () -> userService.authenticate(null, response));
    }

    @Test
    void shouldThrowExceptionWhenUsernameOrPasswordIsNull() {
        //given
        LoginCredentials nullUsername = new LoginCredentials(null, "password");
        LoginCredentials nullPassword = new LoginCredentials("user", null);

        //when
        //then
        assertThrows(AuthenticationException.class, () -> userService.authenticate(nullUsername, response));
        assertThrows(AuthenticationException.class, () -> userService.authenticate(nullPassword, response));
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsIncorrect() {
        //given
        LoginCredentials loginCredentials = new LoginCredentials("user", "password");
        given(userRepository.findByUsername(any(String.class))).willReturn(Optional.empty());

        //when
        //then
        assertThrows(AuthenticationException.class, () -> userService.authenticate(loginCredentials, response));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        //given
        LoginCredentials loginCredentials = new LoginCredentials("user", "wrongpassword");
        Role role = Role.builder().roleName("user").build();
        User user = User.builder().username("user").password("password").role(role).build();
        given(userRepository.findByUsername(any(String.class))).willReturn(Optional.of(user));

        //when
        //then
        assertThrows(AuthenticationException.class, () -> userService.authenticate(loginCredentials, response));
    }

    @Test
    void shouldReturnUserWhenCredentialsAreCorrect() {
        //given
        LoginCredentials loginCredentials = new LoginCredentials("user", "password");
        Role role = Role.builder().roleName("user").build();
        User user = User.builder().username("user").password("password").role(role).build();
        given(userRepository.findByUsername(any(String.class))).willReturn(Optional.of(user));

        //when
        UserDTO userDTO = userService.authenticate(loginCredentials, response);
        //then
        verify(userRepository).findByUsername(loginCredentials.getUsername());
        assertEquals(UserDTO.of(user), userDTO);
    }

}