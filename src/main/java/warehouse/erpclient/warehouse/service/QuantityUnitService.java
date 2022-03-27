package warehouse.erpclient.warehouse.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.model.QuantityUnit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityUnitService {

    public ResponseEntity<RequestResult<String>> getAllQuantityUnits() {
        List<String> quantityUnitSymbols = Arrays.stream(QuantityUnit.values())
                .map(QuantityUnit::getUnitSymbol)
                .collect(Collectors.toList());
        return RequestResult.createResponse(quantityUnitSymbols);
    }

}
