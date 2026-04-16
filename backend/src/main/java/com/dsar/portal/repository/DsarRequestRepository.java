package com.dsar.portal.repository;

import com.dsar.portal.model.DsarRequest;
import com.dsar.portal.model.RequestStatus;
import com.dsar.portal.model.RequestType;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory repository for DsarRequest entities.
 * All operations are thread-safe via ConcurrentHashMap.
 * Sorting is applied at query time (newest first).
 */
@Repository
public class DsarRequestRepository {
    private final Map<String, DsarRequest> store = new ConcurrentHashMap<>();

    public DsarRequest save(DsarRequest request) {
        store.put(request.getId(), request);
        return request;
    }

    public Optional<DsarRequest> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<DsarRequest> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(DsarRequest::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /** Returns only the requests submitted by the given username, newest first. */
    public List<DsarRequest> findBySubmittedBy(String username) {
        return store.values().stream()
                .filter(r -> username.equals(r.getSubmittedBy()))
                .sorted(Comparator.comparing(DsarRequest::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /** Filters by status and/or type. Null values are treated as "no filter". */
    public List<DsarRequest> findByFilters(RequestStatus status, RequestType type) {
        return store.values().stream()
                .filter(r -> status == null || r.getStatus() == status)
                .filter(r -> type == null || r.getType() == type)
                .sorted(Comparator.comparing(DsarRequest::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public long count() {
        return store.size();
    }

    public Map<RequestStatus, Long> countByStatus() {
        return store.values().stream()
                .collect(Collectors.groupingBy(DsarRequest::getStatus, Collectors.counting()));
    }

    public Map<RequestType, Long> countByType() {
        return store.values().stream()
                .collect(Collectors.groupingBy(DsarRequest::getType, Collectors.counting()));
    }

    public void deleteById(String id) {
        store.remove(id);
    }
}
