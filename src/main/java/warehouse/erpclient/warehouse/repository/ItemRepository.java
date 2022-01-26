package warehouse.erpclient.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.erpclient.warehouse.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByWarehouseId(Long warehouseId);

    Optional<Item> findByWarehouseIdAndId(Long warehouseId, Long itemId);

}
