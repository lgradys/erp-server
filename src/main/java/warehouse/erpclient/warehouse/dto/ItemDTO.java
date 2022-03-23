package warehouse.erpclient.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.QuantityUnit;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private long id;

    @NotNull
    private String name;

    @Range(min = 0, max = Integer.MAX_VALUE)
    private int quantity;

    private QuantityUnit quantityUnit;
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
