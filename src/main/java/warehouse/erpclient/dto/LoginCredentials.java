package warehouse.erpclient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginCredentials {

    private final String username;
    private final String password;

}
