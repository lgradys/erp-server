package warehouse.erpclient.warehouse.dto;

import lombok.Builder;
import lombok.Getter;
import warehouse.erpclient.warehouse.model.Warehouse;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class WarehouseDTO {

    private long id;

    @NotNull
    private String name;

    private String street;
    private int streetNumber;
    private String postalCode;
    private String city;

    public static WarehouseDTO of (Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .street(warehouse.getAddress().getStreet())
                .streetNumber(warehouse.getAddress().getStreetNumber())
                .postalCode(warehouse.getAddress().getPostalCode())
                .city(warehouse.getAddress().getCity())
                .build();
    }

}
