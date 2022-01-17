package warehouse.erpclient.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import warehouse.erpclient.authentication.model.User;

import java.util.Objects;

@Getter
@Setter
@Builder
public class UserDTO {

    private String username;
    private String password;
    private String roleName;
    private boolean authenticated;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roleName(user.getRole().getRoleName())
                .authenticated(true)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return authenticated == userDTO.authenticated && username.equals(userDTO.username) && password.equals(userDTO.password) && roleName.equals(userDTO.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roleName, authenticated);
    }
}
