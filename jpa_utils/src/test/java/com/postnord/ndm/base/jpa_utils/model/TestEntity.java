package com.postnord.ndm.base.jpa_utils.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
