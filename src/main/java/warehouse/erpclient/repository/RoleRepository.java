package warehouse.erpclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.erpclient.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
