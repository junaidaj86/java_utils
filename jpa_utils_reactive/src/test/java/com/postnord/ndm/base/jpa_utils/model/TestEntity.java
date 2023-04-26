package com.postnord.ndm.base.jpa_utils.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "TEST_ENTITY")
public class TestEntity extends BaseEntity {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private String id;
}
