package com.postnord.ndm.base.jpa_utils.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "TEST_NAMED_ENTITY")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class TestNamedEntity extends NamedEntity {
}
