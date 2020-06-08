package cyber.punks.wzas.repository;

import cyber.punks.wzas.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<PositionEntity, Long> {

    @Query(value = "SELECT p FROM PositionEntity p WHERE p.accountEntity.login = :login")
    Optional<PositionEntity> findByAccount(String login);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PositionEntity p WHERE p.accountEntity IS NULL")
    void deleteAllTestPositions();

    @Query(value = "SELECT * FROM position WHERE ST_DWithin(current, ST_SetSRID(ST_MakePoint(?1, ?2),4326), ?3)", nativeQuery = true)
    List<PositionEntity> findByRadius(double x, double y, double radius);

    @Query(value = "SELECT * FROM position WHERE ST_DWithin(destination, ST_SetSRID(ST_MakePoint(?1, ?2),4326), ?3)", nativeQuery = true)
    List<PositionEntity> findInRadiusByDestination(double x, double y, double radius);
}
