package warehouse.erpclient.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import warehouse.erpclient.authentication.model.Role;
import warehouse.erpclient.authentication.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Builder
public class UserDTO {

    @NotNull
    @Size(min = 2)
    private String username;

    @NotNull
    @Size(min = 8, max = 45)
    private String password;
    private Role role;
    private boolean enabled;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
