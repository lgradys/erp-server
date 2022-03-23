package warehouse.erpclient.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import warehouse.erpclient.warehouse.model.Warehouse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {

    private long id;

    @NotNull
    private String name;

    @Valid
    private AddressDTO address;

    public static WarehouseDTO of (Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(AddressDTO.of(warehouse.getAddress()))
                .build();
    }

}
