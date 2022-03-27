package warehouse.erpclient.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.service.QuantityUnitService;

@RestController
@RequiredArgsConstructor
public class QuantityUnitController {

    private final QuantityUnitService quantityUnitService;

    @GetMapping("/quantityUnits")
    public ResponseEntity<RequestResult<String>> getQuantityUnits(){
        return quantityUnitService.getAllQuantityUnits();
    }

}
