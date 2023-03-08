package com.postnord.ndm.base.test_utils.model;


import com.postnord.ndm.api.common.validation.constraints.NdmStringLength;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestDataV1 {
    @NdmStringLength(minLength = 8, length = 16)
    private final String id;
    @NdmStringLength(minLength = 8, length = 16)
    private final String name;
}
