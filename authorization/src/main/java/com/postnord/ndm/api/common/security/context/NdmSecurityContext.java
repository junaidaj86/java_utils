package com.postnord.ndm.api.common.security.context;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NdmSecurityContext {
    private Set<String> roles;
    private long exp;
    private String upn;
}
