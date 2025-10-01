package gobov.roma.yclientsv1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "resources")
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "requiredResources")
    private List<Service> services;

    private boolean isActive = true;
    private Integer maxSimultaneousUse = 1;
}