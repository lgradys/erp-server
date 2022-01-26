package warehouse.erpclient.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.common.request_result.Error;
import warehouse.erpclient.common.request_result.RequestResult;
import warehouse.erpclient.warehouse.dto.ItemDTO;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.repository.ItemRepository;
import warehouse.erpclient.warehouse.repository.WarehouseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;

    public ResponseEntity<RequestResult<ItemDTO>> getAllItems(Long warehouseId) {
        List<Item> items = itemRepository.findAllByWarehouseId(warehouseId);
        RequestResult<ItemDTO> requestResult;
        if (items.isEmpty()) {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error()), List.of());
        } else {
            List<ItemDTO> itemDTOS = items.stream()
                    .map(ItemDTO::of)
                    .collect(Collectors.toList());
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), itemDTOS);
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> deleteItem(Long warehouseId, Long itemId) {
        Optional<Item> item = itemRepository.findByWarehouseIdAndId(warehouseId, itemId);
        RequestResult<ItemDTO> requestResult;
        if (item.isPresent()) {
            itemRepository.delete(item.get());
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of());
        } else {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error(itemId, Item.class)), List.of());
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    public ResponseEntity<RequestResult<ItemDTO>> getItem(Long warehouseId, Long itemId) {
        Optional<Item> item = itemRepository.findByWarehouseIdAndId(warehouseId, itemId);
        RequestResult<ItemDTO> requestResult;
        requestResult = item.map(value -> new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(ItemDTO.of(value))))
                .orElseGet(() -> new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error(itemId, Item.class)), List.of()));
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> addItem(Long warehouseId, ItemDTO itemDTO) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        RequestResult<ItemDTO> requestResult;
        if (warehouse.isPresent()) {
            Warehouse loadedWarehouse = warehouse.get();
            Item item = Item.of(itemDTO);
            item.setWarehouse(loadedWarehouse);
            Item savedItem = itemRepository.save(item);
            requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(ItemDTO.of(savedItem)));
        } else {
            requestResult = new RequestResult<>(HttpStatus.NOT_FOUND.value(), List.of(new Error(warehouseId, Warehouse.class)), List.of());
        }
        return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> editItem(Long warehouseId, Long itemId, ItemDTO itemDTO) {
        return itemRepository.findByWarehouseIdAndId(warehouseId, itemId).map(item -> {
            item.setName(itemDTO.getName());
            item.setQuantity(itemDTO.getQuantity());
            item.setQuantityUnit(itemDTO.getQuantityUnit());
            Item editedItem = itemRepository.save(item);
            RequestResult<ItemDTO> requestResult = new RequestResult<>(HttpStatus.OK.value(), List.of(), List.of(ItemDTO.of(editedItem)));
            return new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }).orElseGet(() -> addItem(warehouseId, itemDTO));
    }

}
