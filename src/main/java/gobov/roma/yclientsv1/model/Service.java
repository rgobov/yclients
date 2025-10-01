package gobov.roma.yclientsv1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "services")
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "services")
    private List<MasterProfile> masters;

    @ManyToMany
    @JoinTable(
            name = "service_resource",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private List<Resource> requiredResources;

    private String category;
    private String imageUrl;
    private boolean isActive = true;
    private Integer sortOrder = 0;
}