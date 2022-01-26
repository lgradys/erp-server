package warehouse.erpclient.authentication.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import warehouse.erpclient.request_result.RequestResult;
import warehouse.erpclient.authentication.dto.UserDTO;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;
import warehouse.erpclient.authentication.repository.RoleRepository;
import warehouse.erpclient.authentication.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private RoleRepository roleRepository;

    private static UserDTO userDTO;
    private static User user;
    private static Role role;

    @BeforeAll
    static void beforeAll() {
        role = Role.builder()
                .roleName("user")
                .build();
        userDTO = UserDTO.builder()
                .username("user")
                .password("password")
                .roleName(role.getRoleName())
                .build();
        user = User.builder()
                .id(1)
                .username("user")
                .password("password")
                .role(role)
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
                .password("password")
                .role(role)
                .build();
        given(userRepository.findByUsername(newUser.getUsername())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(newUser);

        //when
        RequestResult<UserDTO> requestResult = userService.addUser(UserDTO.of(newUser)).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getResource()).size().isEqualTo(1);
        assertEquals(newUser.getUsername(), requestResult.getResource().get(0).getUsername());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsAlreadyUsed() {
        //given
        String alreadyUsedUsername = user.getUsername();
        given(userRepository.findByUsername(alreadyUsedUsername)).willReturn(Optional.of(user));

        //when
        RequestResult<UserDTO> requestResult = userService.addUser(userDTO).getBody();

        //then
        assertNotNull(requestResult);
        assertThat(requestResult.getError()).size().isEqualTo(1);
    }

}