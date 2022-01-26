package warehouse.erpclient.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.erpclient.warehouse.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
