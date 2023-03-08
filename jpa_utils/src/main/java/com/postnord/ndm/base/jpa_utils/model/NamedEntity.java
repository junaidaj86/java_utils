package com.postnord.ndm.base.jpa_utils.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.postnord.ndm.base.jpa_utils.utils.ConstantsHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class NamedEntity extends BaseEntityWithUUID {
    @Column(name = "NAME", length = ConstantsHelper.NAME_MAX_LENGTH, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = ConstantsHelper.DESCRIPTION_MAX_LENGTH)
    private String description;
}
