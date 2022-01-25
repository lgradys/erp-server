package warehouse.erpclient.warehouse.model;

import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    private int quantity;
    private String quantityUnit;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

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
