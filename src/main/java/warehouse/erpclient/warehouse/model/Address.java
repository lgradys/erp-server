package warehouse.erpclient.warehouse.model;

import lombok.*;
import warehouse.erpclient.warehouse.dto.AddressDTO;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && Objects.equals(street, address.street) && Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, streetNumber, postalCode, city);
    }

}
