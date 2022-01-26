package warehouse.erpclient.request_result;

import lombok.Getter;

@Getter
public class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }

    public Error(Long id, Class<?> className) {
        this.message = "Could not find any " + className.getSimpleName() + " with id: " + id;
    }

}
