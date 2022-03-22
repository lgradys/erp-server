package warehouse.erpclient.authentication.model;

import lombok.*;
import warehouse.erpclient.authentication.dto.UserDTO;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private byte[] password;

    private Role role;
    private boolean enabled;

    public static User of(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword().getBytes(StandardCharsets.UTF_8))
                .role(userDTO.getRole())
                .enabled(true)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

}
