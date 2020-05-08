package cyber.punks.wzas.repository;

import cyber.punks.wzas.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByLogin(String login);

    List<AccountEntity> findAll();
}
