package com.postnord.ndm.base.jpa_utils.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import java.time.Instant;

import javax.persistence.*;

import com.postnord.ndm.base.jpa_utils.utils.ConstantsHelper;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseEntity {
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;
    @Column(name = "UPDATED_AT", nullable = false)
    private Instant updatedAt;
    @Version
    @Column(nullable = false)
    private int version;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = ConstantsHelper.ENUM_STATUS_MAX_LENGTH, nullable = false)
    private Status status;

    @PrePersist
    public void prePersist() {
        createdAt = InstantHelper.getInstantAndStripNano();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = InstantHelper.getInstantAndStripNano();
    }
}
