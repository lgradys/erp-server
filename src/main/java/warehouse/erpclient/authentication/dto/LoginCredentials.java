package warehouse.erpclient.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {

    @NotNull
    @Size(min = 2, max = 45)
    private String username;

    @NotNull
    @Size(min = 8, max = 45)
    private String password;

}
