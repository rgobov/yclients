package gobov.roma.yclientsv1.repository;


import gobov.roma.yclientsv1.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.master.id = :masterId
        AND a.startTime < :endTime
        AND a.endTime > :startTime
        AND a.status IN ('CONFIRMED', 'PENDING')
    """)
    List<Appointment> findByMasterProfileIdAndOverlap(
            @Param("masterId") Long masterId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.resource.id = :resourceId
        AND a.startTime < :endTime
        AND a.endTime > :startTime
        AND a.status IN ('CONFIRMED', 'PENDING')
    """)
    List<Appointment> findByResourceIdAndOverlap(
            @Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.master.id = :masterId
        AND a.startTime >= :dateStart
        AND a.endTime <= :dateEnd
        AND a.status IN ('CONFIRMED', 'PENDING')
    """)
    List<Appointment> findByMasterProfileIdAndTimeRange(
            @Param("masterId") Long masterId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.resource.id = :resourceId
        AND a.startTime >= :dateStart
        AND a.endTime <= :dateEnd
        AND a.status IN ('CONFIRMED', 'PENDING')
    """)
    List<Appointment> findByResourceIdAndTimeRange(
            @Param("resourceId") Long resourceId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.company.id = :companyId
        AND a.startTime >= :dateStart
        AND a.endTime <= :dateEnd
        AND a.status IN ('CONFIRMED', 'PENDING')
    """)
    List<Appointment> findByCompanyIdAndTimeRange(
            @Param("companyId") Long companyId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );
}