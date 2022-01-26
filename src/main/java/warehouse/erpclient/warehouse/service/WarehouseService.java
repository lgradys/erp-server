package warehouse.erpclient.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.request_result.Error;
import warehouse.erpclient.request_result.RequestResult;
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
        RequestResult<WarehouseDTO> requestResult;
        requestResult = warehouse.map(value -> new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(WarehouseDTO.of(value))))
                .orElseGet(() -> new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error(warehouseId, Warehouse.class)), List.of()));
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    public ResponseEntity<RequestResult<WarehouseDTO>> addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseRepository.save(Warehouse.of(warehouseDTO));
        RequestResult<WarehouseDTO> requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(WarehouseDTO.of(warehouse)));
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<WarehouseDTO>> deleteWarehouse(Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        RequestResult<WarehouseDTO> requestResult;
        if (warehouse.isPresent()) {
            warehouseRepository.delete(warehouse.get());
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of());
        } else {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error(warehouseId, Warehouse.class)), List.of());
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<WarehouseDTO>> editWarehouse(Long warehouseId, WarehouseDTO warehouseDTO) {
        Warehouse editedWarehouse = warehouseRepository.findById(warehouseId).map(warehouse -> {
            warehouse.setName(warehouseDTO.getName());
            warehouse.getAddress().setStreet(warehouseDTO.getAddress().getStreet());
            warehouse.getAddress().setStreetNumber(warehouseDTO.getAddress().getStreetNumber());
            warehouse.getAddress().setPostalCode(warehouseDTO.getAddress().getPostalCode());
            warehouse.getAddress().setCity(warehouseDTO.getAddress().getCity());
            return warehouseRepository.save(warehouse);
        }).orElseGet(() -> {
            Warehouse warehouse = Warehouse.of(warehouseDTO);
            return warehouseRepository.save(warehouse);
        });
        RequestResult<WarehouseDTO> requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(WarehouseDTO.of(editedWarehouse)));
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }


    public ResponseEntity<RequestResult<WarehouseDTO>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        RequestResult<WarehouseDTO> requestResult;
        if (warehouses.isEmpty()) {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error("Could not find any resource!")), List.of());
        } else {
            List<WarehouseDTO> warehouseDTOS = warehouses.stream()
                    .map(WarehouseDTO::of)
                    .collect(Collectors.toList());
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), warehouseDTOS);
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

}
