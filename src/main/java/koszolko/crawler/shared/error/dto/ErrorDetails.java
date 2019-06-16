package koszolko.crawler.shared.error.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private final String traceId;
    private String timestamp;
    private String message;
}