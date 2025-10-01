package gobov.roma.yclientsv1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "master_profile_id", nullable = false)
    private MasterProfile master;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status = "PENDING"; // PENDING, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW
    private String notes;

    private Integer durationMinutes;
    private BigDecimal totalPrice;

    private String paymentStatus = "NOT_PAID"; // NOT_PAID, PAID, REFUNDED

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (service != null) {
            this.durationMinutes = service.getDurationMinutes();
            this.totalPrice = service.getPrice();
        }
        if (startTime != null && durationMinutes != null) {
            this.endTime = startTime.plusMinutes(durationMinutes);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}