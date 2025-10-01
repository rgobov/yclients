package gobov.roma.yclientsv1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacations")
@Data
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "master_profile_id", nullable = false)
    private MasterProfile master;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String reason; // "Отпуск", "Больничный", "Конференция"

    private boolean isActive = true;
}