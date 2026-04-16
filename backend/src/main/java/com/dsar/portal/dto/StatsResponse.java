package com.dsar.portal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/** Admin dashboard statistics DTO. */
@Data
@Builder
public class StatsResponse {
    private long total;
    private Map<String, Long> byStatus;
    private Map<String, Long> byType;
    private long auditLogs;
}
