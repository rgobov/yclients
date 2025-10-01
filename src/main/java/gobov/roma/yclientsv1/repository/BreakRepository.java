package gobov.roma.yclientsv1.repository;

import com.yclientsv1.model.Break;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BreakRepository extends JpaRepository<Break, Long> {

    @Query("""
        SELECT b FROM Break b
        WHERE b.master.id = :masterId
        AND b.startTime < :endTime
        AND b.endTime > :startTime
        AND b.isActive = true
    """)
    List<Break> findByMasterProfileIdAndOverlap(
            @Param("masterId") Long masterId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}