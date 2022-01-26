package warehouse.erpclient.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.erpclient.warehouse.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
