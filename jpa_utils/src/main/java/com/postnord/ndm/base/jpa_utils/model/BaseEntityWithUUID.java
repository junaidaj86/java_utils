package com.postnord.ndm.base.jpa_utils.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
