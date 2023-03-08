package com.postnord.ndm.base.jpa_utils.model;

import javax.persistence.Entity;
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
@Table(name = "TEST_NAMED_ENTITY")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class TestNamedEntity extends NamedEntity {
}
