package warehouse.erpclient.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.dto.ItemDTO;
import warehouse.erpclient.warehouse.service.ItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{warehouseId}/items")
    public ResponseEntity<RequestResult<ItemDTO>> getAllItems(@PathVariable Long warehouseId) {
        return itemService.getItems(warehouseId);
    }

    @GetMapping("/{warehouseId}/items/{itemId}")
    public ResponseEntity<RequestResult<ItemDTO>> getItem(@PathVariable Long warehouseId, @PathVariable Long itemId) {
        return itemService.getItem(warehouseId, itemId);
    }

    @PostMapping("/{warehouseId}/items")
    public ResponseEntity<RequestResult<ItemDTO>> addItem(@PathVariable Long warehouseId,
                                                          @RequestBody @Valid ItemDTO itemDTO) {
        return itemService.addItem(warehouseId, itemDTO);
    }

    @PutMapping("/{warehouseId}/items/{itemId}")
    public ResponseEntity<RequestResult<ItemDTO>> addItem(@PathVariable Long warehouseId, @PathVariable Long itemId,
                                                          @RequestBody @Valid ItemDTO itemDTO) {
        return itemService.editItem(warehouseId, itemId, itemDTO);
    }

    @DeleteMapping("/{warehouseId}/items/{itemId}")
    public ResponseEntity<RequestResult<ItemDTO>> deleteItem(@PathVariable Long warehouseId, @PathVariable Long itemId) {
        return itemService.deleteItem(warehouseId, itemId);
    }

}
