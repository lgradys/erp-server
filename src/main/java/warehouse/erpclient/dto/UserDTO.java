package warehouse.erpclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import warehouse.erpclient.model.User;

@Getter
@Setter
@Builder
public class UserDTO {

    private String username;
    private String password;
    private boolean authenticated;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authenticated(true)
                .build();
    }

    public static UserDTO createUnauthenticatedUserDTO() {
        return UserDTO.builder()
                .authenticated(false)
                .build();
    }

}
