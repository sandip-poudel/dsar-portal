package com.dsar.portal.repository;

import com.dsar.portal.model.AuditLog;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory repository for AuditLog entries.
 * Entries are never deleted — the audit trail is immutable for compliance.
 */
@Repository
public class AuditLogRepository {
    private final Map<String, AuditLog> store = new ConcurrentHashMap<>();

    public AuditLog save(AuditLog log) {
        store.put(log.getId(), log);
        return log;
    }

    /** Returns all audit logs, newest first. */
    public List<AuditLog> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(AuditLog::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    /** Returns logs for a specific request, oldest first (chronological timeline). */
    public List<AuditLog> findByRequestId(String requestId) {
        return store.values().stream()
                .filter(l -> requestId.equals(l.getRequestId()))
                .sorted(Comparator.comparing(AuditLog::getTimestamp))
                .collect(Collectors.toList());
    }

    public long count() {
        return store.size();
    }
}
