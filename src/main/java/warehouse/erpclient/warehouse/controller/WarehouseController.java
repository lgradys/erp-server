package warehouse.erpclient.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.dto.WarehouseDTO;
import warehouse.erpclient.warehouse.service.WarehouseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<RequestResult<WarehouseDTO>> getAllWarehouses() {
        return warehouseService.getWarehouses();
    }

    @GetMapping("/warehouses/{warehouseId}")
    public ResponseEntity<RequestResult<WarehouseDTO>> getWarehouse(@PathVariable Long warehouseId) {
        return warehouseService.getWarehouse(warehouseId);
    }

    @PostMapping("/warehouses")
    public ResponseEntity<RequestResult<WarehouseDTO>> addWarehouse(@RequestBody @Valid WarehouseDTO warehouseDTO) {
        return warehouseService.addWarehouse(warehouseDTO);
    }

    @PutMapping("/warehouses/{warehouseId}")
    public ResponseEntity<RequestResult<WarehouseDTO>> editWarehouse(@PathVariable Long warehouseId,
                                                                     @RequestBody @Valid WarehouseDTO warehouseDTO) {
        return warehouseService.editWarehouse(warehouseId, warehouseDTO);
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public ResponseEntity<RequestResult<WarehouseDTO>> editWarehouse(@PathVariable Long warehouseId) {
        return warehouseService.deleteWarehouse(warehouseId);
    }

}
