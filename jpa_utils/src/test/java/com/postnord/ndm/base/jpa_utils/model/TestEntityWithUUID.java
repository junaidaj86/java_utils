package com.postnord.ndm.base.jpa_utils.model;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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
@Table(
        name = "TEST_ENTITY_W_UUID",
        indexes = @Index(columnList = "TEST_NUMBER", name = "IX_TEST_ENTITY_1")
)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class TestEntityWithUUID extends BaseEntityWithUUID {
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "TEST_NUMBER", nullable = false)
    private int number;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TestType type;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "TEST_ENTITY_STRINGS",
            joinColumns = @JoinColumn(name = "TEST_ENTITY_ID", foreignKey = @ForeignKey(name = "FK_TEST_ENTITY_STRINGS_1")),
            indexes = @Index(columnList = "TEST_ENTITY_ID", name = "IX_TEST_ENTITY_STRINGS_1")
    )
    @Column(name = "TEST_STRING", nullable = false)
    private List<String> listOfStrings;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "TEST_ENTITY_TIMES",
            joinColumns = @JoinColumn(name = "TEST_ENTITY_ID", foreignKey = @ForeignKey(name = "FK_TEST_ENTITY_TIMES_1")),
            indexes = @Index(columnList = "TEST_ENTITY_ID", name = "IX_TEST_ENTITY_TIMES_1")
    )
    @Column(name = "TEST_TIME", nullable = false)
    private Set<Instant> setOfTimes;

}
