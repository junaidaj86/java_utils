package com.postnord.ndm.base.jpa_utils.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseEntityWithUUID extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "UUID", nullable = false, updatable = false)
    private UUID id;
}
