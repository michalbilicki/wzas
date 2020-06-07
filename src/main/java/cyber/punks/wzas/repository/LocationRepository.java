package cyber.punks.wzas.repository;

import cyber.punks.wzas.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<PositionEntity, Long> {

    @Query(value = "SELECT p FROM PositionEntity p WHERE p.accountEntity.login = :login")
    Optional<PositionEntity> findByAccount(String login);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PositionEntity p WHERE p.accountEntity IS NULL")
    void deleteAllTestPositions();
}
