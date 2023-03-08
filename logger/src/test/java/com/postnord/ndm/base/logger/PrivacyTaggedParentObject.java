package com.postnord.ndm.base.logger;

import lombok.Getter;

@Getter
public class PrivacyTaggedParentObject {
    @NdmPrivacyTag(NdmPrivacyType.IMSI)
    private final String taggedImsiParentField;
    @NdmPrivacyTag(NdmPrivacyType.NOT_SET)
    private final String taggedNonSetParentField;
    private final Long nonTaggedParentField;

    public PrivacyTaggedParentObject(final String taggedImsiParentField,
                                     final String taggedNonSetParentField,
                                     final Long nonTaggedParentField) {
        this.taggedImsiParentField = taggedImsiParentField;
        this.taggedNonSetParentField = taggedNonSetParentField;
        this.nonTaggedParentField = nonTaggedParentField;
    }
}
