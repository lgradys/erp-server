package warehouse.erpclient.warehouse.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import warehouse.erpclient.warehouse.model.Item;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ItemDTO {

    private long id;

    @NotNull
    private String name;

    @Range(min = 0, max = Integer.MAX_VALUE)
    private int quantity;

    private String quantityUnit;
    private long warehouseId;

    public static ItemDTO of(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .quantityUnit(item.getQuantityUnit())
                .warehouseId(item.getWarehouse().getId())
                .build();
    }

}
