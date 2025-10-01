package com.yclientsv1.model;

import gobov.roma.yclientsv1.model.Company;
import gobov.roma.yclientsv1.model.MasterProfile;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "breaks")
@Data
public class Break {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "master_profile_id", nullable = false)
    private MasterProfile master;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String reason; // "Обед", "Тренинг", "Выходной"

    private boolean isActive = true;
}