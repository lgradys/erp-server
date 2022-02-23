package warehouse.erpclient.warehouse.model;

import lombok.*;
import warehouse.erpclient.warehouse.dto.WarehouseDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(name, warehouse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Item addItem(Item item) {
        if (items == null) {
            items = new HashSet<>();
        }
        items.add(item);
        return item;
    }

    public static Warehouse of (WarehouseDTO warehouseDTO) {
        return Warehouse.builder()
                .name(warehouseDTO.getName())
                .address(Address.of(warehouseDTO.getAddress()))
                .build();
    }

    public void editWarehouse(WarehouseDTO warehouseDTO) {
        setName(warehouseDTO.getName());
        getAddress().setStreet(warehouseDTO.getAddress().getStreet());
        getAddress().setStreetNumber(warehouseDTO.getAddress().getStreetNumber());
        getAddress().setPostalCode(warehouseDTO.getAddress().getPostalCode());
        getAddress().setCity(warehouseDTO.getAddress().getCity());
    }

}
