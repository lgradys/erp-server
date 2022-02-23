package warehouse.erpclient.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.erpclient.utils.dto.RequestResult;
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

    public ResponseEntity<RequestResult<ItemDTO>> getItems(Long warehouseId) {
        List<Item> items = itemRepository.findAllByWarehouseId(warehouseId);
        List<ItemDTO> itemDTOS = items.stream()
                .map(ItemDTO::of)
                .collect(Collectors.toList());
        return RequestResult.createResponse(itemDTOS);
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> deleteItem(Long warehouseId, Long itemId) {
        Optional<Item> item = itemRepository.findByWarehouseIdAndId(warehouseId, itemId);
        ItemDTO itemDTO = item.map(value -> {
            itemRepository.delete(value);
            return ItemDTO.of(value);
        }).orElse(null);
        return RequestResult.createResponse(itemDTO);
    }

    public ResponseEntity<RequestResult<ItemDTO>> getItem(Long warehouseId, Long itemId) {
        Optional<Item> item = itemRepository.findByWarehouseIdAndId(warehouseId, itemId);
        ItemDTO itemDTO = item.map(ItemDTO::of)
                .orElse(null);
        return RequestResult.createResponse(itemDTO);
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> addItem(Long warehouseId, ItemDTO itemDTO) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        ItemDTO savedItemDTO = warehouse.map(value -> {
            Item item = Item.of(itemDTO);
            item.setWarehouse(value);
            Item savedItem = itemRepository.save(item);
            return ItemDTO.of(savedItem);
        }).orElse(null);
        return RequestResult.createResponse(savedItemDTO);
    }

    @Transactional
    public ResponseEntity<RequestResult<ItemDTO>> editItem(Long warehouseId, Long itemId, ItemDTO itemDTO) {
        return itemRepository.findByWarehouseIdAndId(warehouseId, itemId).map(item -> {
            item.editItem(itemDTO);
            Item editedItem = itemRepository.save(item);
            return RequestResult.createResponse(ItemDTO.of(editedItem));
        }).orElseGet(() -> addItem(warehouseId, itemDTO));
    }

}
