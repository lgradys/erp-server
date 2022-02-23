package warehouse.erpclient.utils.dto;

import lombok.Getter;

@Getter
public class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }

}
