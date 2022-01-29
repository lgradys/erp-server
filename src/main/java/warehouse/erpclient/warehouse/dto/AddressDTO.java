package warehouse.erpclient.warehouse.dto;

import lombok.Builder;
import lombok.Getter;
import warehouse.erpclient.warehouse.model.Address;
import warehouse.erpclient.warehouse.validator.PostalCode;

@Getter
@Builder
public class AddressDTO {

    private long id;
    private String street;
    private int streetNumber;

    @PostalCode
    private String postalCode;
    private String city;

    public static AddressDTO of (Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .build();
    }

}