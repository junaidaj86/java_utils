package com.postnord.ndm.base.logger;

import lombok.Getter;

@Getter
public class PrivacyTaggedObject extends PrivacyTaggedParentObject {
    @NdmPrivacyTag(NdmPrivacyType.IMSI)
    private final String taggedImsiField;
    @NdmPrivacyTag(NdmPrivacyType.NOT_SET)
    private final String taggedNonSetField;
    private final Long nonTaggedField;

    public PrivacyTaggedObject(final String taggedImsiField, final String taggedNonSetField, final Long nonTaggedField) {
        super(taggedImsiField, taggedNonSetField, nonTaggedField);
        this.taggedImsiField = taggedImsiField;
        this.taggedNonSetField = taggedNonSetField;
        this.nonTaggedField = nonTaggedField;
    }
}
