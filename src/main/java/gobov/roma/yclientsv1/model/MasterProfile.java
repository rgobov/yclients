package gobov.roma.yclientsv1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "master_profiles")
@Data
public class MasterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String specialization;
    private String experience;
    private String studio;
    private String avatarUrl;
    private String videoIntroUrl;

    @ManyToMany
    @JoinTable(
            name = "master_service",
            joinColumns = @JoinColumn(name = "master_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private boolean isActive = true;
    private boolean isVerified = false;
}