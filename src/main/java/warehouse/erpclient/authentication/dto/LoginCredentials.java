package warehouse.erpclient.authentication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class LoginCredentials {

    @NotNull
    @Size(min = 2, max = 45)
    private final String username;

    @NotNull
    @Size(min = 8, max = 45)
    private final String password;

}
