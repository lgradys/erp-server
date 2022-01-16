package warehouse.erpclient.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import warehouse.erpclient.authentication.model.User;

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

}
