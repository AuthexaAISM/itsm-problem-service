package com.itsm.problem.rest;

import com.itsm.problem.domain.ProblemRecord;
import com.itsm.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ProblemRecord> createProblem(
            @PathVariable String tenantId,
            @RequestBody ProblemRecord problemRecord) {
        return ResponseEntity.ok(problemService.createProblem(tenantId, problemRecord));
    }

    @GetMapping
    public ResponseEntity<List<ProblemRecord>> getAllProblems(@PathVariable String tenantId) {
        return ResponseEntity.ok(problemService.getAllProblems(tenantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemRecord> getProblem(
            @PathVariable String tenantId,
            @PathVariable String id) {
        return problemService.getProblem(id, tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/root-cause")
    public ResponseEntity<ProblemRecord> updateRootCause(
            @PathVariable String tenantId,
            @PathVariable String id,
            @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(problemService.updateRootCause(id, tenantId, payload.get("rootCause"), payload.get("workaround")));
    }
}
