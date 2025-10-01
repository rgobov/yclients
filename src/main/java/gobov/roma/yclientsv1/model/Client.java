package gobov.roma.yclientsv1.model;

import gobov.roma.yclientsv1.model.Company;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String fullName;
    private String phone;
    private String birthDate;
    private String gender;
    private String preferences;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private Integer totalVisits = 0;
    private Integer totalSpent = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}