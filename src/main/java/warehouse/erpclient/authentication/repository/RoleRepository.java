package warehouse.erpclient.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.erpclient.authentication.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
