package gobov.roma.yclientsv1.repository;


import gobov.roma.yclientsv1.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("""
        SELECT v FROM Vacation v
        WHERE v.master.id = :masterId
        AND v.startDate < :endTime
        AND v.endDate > :startTime
        AND v.isActive = true
    """)
    List<Vacation> findByMasterProfileIdAndOverlap(
            @Param("masterId") Long masterId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}