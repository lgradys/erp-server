package warehouse.erpclient.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("U"),
    ADMIN("A");

    private String code;

}
