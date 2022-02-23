package warehouse.erpclient.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.dto.WarehouseDTO;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.repository.WarehouseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public ResponseEntity<RequestResult<WarehouseDTO>> getWarehouse(Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        WarehouseDTO warehouseDTO = warehouse.map(WarehouseDTO::of)
                .orElse(null);
        return RequestResult.createResponse(warehouseDTO);
    }

    public ResponseEntity<RequestResult<WarehouseDTO>> addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseRepository.save(Warehouse.of(warehouseDTO));
        return RequestResult.createResponse(WarehouseDTO.of(warehouse));
    }

    @Transactional
    public ResponseEntity<RequestResult<WarehouseDTO>> deleteWarehouse(Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        WarehouseDTO warehouseDTO = warehouse.map(value -> {
                warehouseRepository.delete(value);
                return WarehouseDTO.of(value);
                })
                .orElse(null);
        return RequestResult.createResponse(warehouseDTO);
    }

    @Transactional
    public ResponseEntity<RequestResult<WarehouseDTO>> editWarehouse(Long warehouseId, WarehouseDTO warehouseDTO) {
        Warehouse editedWarehouse = warehouseRepository.findById(warehouseId).map(warehouse -> {
            warehouse.editWarehouse(warehouseDTO);
            return warehouseRepository.save(warehouse);
        }).orElseGet(() -> {
            Warehouse warehouse = Warehouse.of(warehouseDTO);
            return warehouseRepository.save(warehouse);
        });
        return RequestResult.createResponse(WarehouseDTO.of(editedWarehouse));
    }

    public ResponseEntity<RequestResult<WarehouseDTO>> getWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        List<WarehouseDTO> warehouseDTOS = warehouses.stream()
                .map(WarehouseDTO::of)
                .collect(Collectors.toList());
        return RequestResult.createResponse(warehouseDTOS);
    }

}
