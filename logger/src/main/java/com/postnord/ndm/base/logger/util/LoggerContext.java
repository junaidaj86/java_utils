package com.postnord.ndm.base.logger.util;

import jakarta.enterprise.context.RequestScoped;

import io.quarkus.arc.Unremovable;
import lombok.Data;

@Data
@Unremovable
@RequestScoped
public class LoggerContext {
    private String flowId;
    private String upn;
    private String classId;
    private String methodId;
}
