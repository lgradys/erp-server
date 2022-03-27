package warehouse.erpclient.warehouse.model;

import lombok.*;
import warehouse.erpclient.utils.exception.EnumConverterException;
import warehouse.erpclient.warehouse.dto.ItemDTO;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "warehouse_id"}))
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    private int quantity;
    private QuantityUnit quantityUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public static Item of (ItemDTO itemDTO) {
        return Item.builder()
                .name(itemDTO.getName())
                .quantity(itemDTO.getQuantity())
                .quantityUnit(getQuantityUnit(itemDTO.getQuantityUnit()))
                .build();
    }

    public void editItem(ItemDTO itemDTO) {
        setName(itemDTO.getName());
        setQuantity(itemDTO.getQuantity());
        setQuantityUnit(getQuantityUnit(itemDTO.getQuantityUnit()));
    }

    private static QuantityUnit getQuantityUnit(String quantityUnitSymbol){
        return Arrays.stream(QuantityUnit.values())
                .filter(quantityUnit -> quantityUnit.getUnitSymbol().equals(quantityUnitSymbol))
                .findFirst()
                .orElseThrow(() -> new EnumConverterException("Illegal quantity unit symbol!"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(warehouse, item.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, warehouse);
    }
}
