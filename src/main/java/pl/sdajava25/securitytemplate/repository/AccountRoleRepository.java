package pl.sdajava25.securitytemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sdajava25.securitytemplate.model.AccountRole;

import java.util.Optional;
@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByName(String name);
    boolean existsByName(String name);
}
