package warehouse.erpclient.utils.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class RequestResult<T> {

    private final int status;
    private final List<Error> error;
    private final List<T> resource;

    public static <T> ResponseEntity<RequestResult<T>> createResponse(List<T> resource) {
        RequestResult<T> requestResult = createRequestResult(resource);
        return new ResponseEntity<>(requestResult, HttpStatus.valueOf(requestResult.status));
    }

    private static <T> RequestResult<T> createRequestResult(List<T> resource) {
        if (resource.isEmpty()) {
            return new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error("Could not find any resource!")), List.of());
        } else {
            return new RequestResult<>(HttpStatus.OK.value(), List.of(), resource);
        }
    }

    public static <T> ResponseEntity<RequestResult<T>> createResponse(T resource) {
        RequestResult<T> requestResult = createRequestResult(resource);
        return new ResponseEntity<>(requestResult, HttpStatus.valueOf(requestResult.status));
    }

    private static <T> RequestResult<T> createRequestResult(T resource) {
        if (resource == null ) {
            return new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error("Could not find any resource!")), List.of());
        } else {
            return new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(resource));
        }
    }

}
