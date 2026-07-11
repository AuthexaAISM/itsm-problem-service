package com.itsm.problem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDateTime;

@Entity
@Table(name = "problem_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = String.class)})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class ProblemRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String status; // OPEN, UNDER_INVESTIGATION, KNOWN_ERROR, RESOLVED, CLOSED

    @Column(length = 4000)
    private String rootCause;

    @Column(length = 4000)
    private String workaround;

    private String assignedTo;

    private String severity; // CRITICAL, HIGH, MEDIUM, LOW

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "OPEN";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
