package warehouse.erpclient.authentication.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import warehouse.erpclient.authentication.component.PasswordEncoder;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.UserRepository;
import warehouse.erpclient.utils.dto.RequestResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static UserDTO userDTO;
    private static User user;

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

    @Test
    void shouldReturnUserWhenUsernameIsCorrect() {
        //given
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        RequestResult<UserDTO> requestResult = userService.getUser(user.getUsername()).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getResource()).size().isEqualTo(1);
        assertEquals(userDTO.getUsername(), requestResult.getResource().get(0).getUsername());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsIncorrect() {
        //given
        String username = "incorrectUsername";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        RequestResult<UserDTO> requestResult = userService.getUser(username).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getError()).size().isEqualTo(1);
    }

    @Test
    void shouldReturnNewUserWhenUsernameIsUnique() {
        //given
        User newUser = User.builder()
                .username("newUser")
                .password("password".getBytes(StandardCharsets.UTF_8))
                .role(Role.USER)
                .build();
        UserDTO userDTO = UserDTO.of(newUser);
        userDTO.setPassword(new String(newUser.getPassword(), StandardCharsets.UTF_8));
        given(userRepository.save(newUser)).willReturn(newUser);
        given(passwordEncoder.encode(newUser.getPassword())).willReturn(newUser.getPassword());

        //when
        RequestResult<UserDTO> requestResult = userService.addUser(userDTO).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getResource()).size().isEqualTo(1);
        assertEquals(newUser.getUsername(), requestResult.getResource().get(0).getUsername());
    }

}