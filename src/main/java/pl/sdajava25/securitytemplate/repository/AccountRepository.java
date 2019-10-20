package pl.sdajava25.securitytemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdajava25.securitytemplate.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);
}
