package warehouse.erpclient.authentication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RequestResult<T> {

    private final int status;
    private final List<Error> error;
    private final List<T> resource;

}
