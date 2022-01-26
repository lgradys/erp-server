package warehouse.erpclient.warehouse.model;

import lombok.*;
import warehouse.erpclient.warehouse.dto.AddressDTO;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"street", "streetNumber"})})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String street;
    private int streetNumber;
    private String postalCode;
    private String city;

    public static Address of(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .streetNumber(addressDTO.getStreetNumber())
                .postalCode(addressDTO.getPostalCode())
                .city(addressDTO.getCity())
                .build();
    }

}
