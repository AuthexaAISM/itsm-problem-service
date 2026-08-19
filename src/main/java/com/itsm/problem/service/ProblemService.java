package com.itsm.problem.service;

import com.itsm.problem.domain.ProblemRecord;
import com.itsm.problem.domain.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public ProblemRecord createProblem(String tenantId, ProblemRecord problemRecord) {
        problemRecord.setTenantId(tenantId);
        return problemRepository.save(problemRecord);
    }

    public List<ProblemRecord> getAllProblems(String tenantId) {
        return problemRepository.findByTenantId(tenantId);
    }

    public Optional<ProblemRecord> getProblem(String id, String tenantId) {
        return problemRepository.findByIdAndTenantId(id, tenantId);
    }

    @Transactional
    public ProblemRecord updateRootCause(String id, String tenantId, String rootCause, String workaround) {
        ProblemRecord record = getProblem(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        record.setRootCause(rootCause);
        record.setWorkaround(workaround);
        record.setStatus("KNOWN_ERROR");
        return problemRepository.save(record);
    }

    @Transactional
    public ProblemRecord updateProblem(String id, String tenantId, ProblemRecord update) {
        ProblemRecord record = getProblem(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        if (update.getTitle() != null) record.setTitle(update.getTitle());
        if (update.getDescription() != null) record.setDescription(update.getDescription());
        if (update.getStatus() != null) record.setStatus(update.getStatus());
        if (update.getSeverity() != null) record.setSeverity(update.getSeverity());
        if (update.getAssignedTo() != null) record.setAssignedTo(update.getAssignedTo());
        return problemRepository.save(record);
    }

    @Transactional
    public void deleteProblem(String id, String tenantId) {
        ProblemRecord record = getProblem(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        problemRepository.delete(record);
        log.info("Deleted problem {} for tenant {}", id, tenantId);
    }
}
