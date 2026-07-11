package com.itsm.problem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemRecord, String> {
    List<ProblemRecord> findByTenantId(String tenantId);
    Optional<ProblemRecord> findByIdAndTenantId(String id, String tenantId);
}
